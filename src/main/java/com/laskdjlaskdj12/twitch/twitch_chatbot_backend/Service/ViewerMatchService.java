package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.MatchInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.ViewerApplyMatchDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.WinnerDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Generator.LinkGenerator;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.StartLotteryDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ViewerMatchApplyDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.Error.BusinessException;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewerMatchService {

	@Autowired
	private MatchInfoDAO matchInfoDAO;

	@Autowired
	private ViewerApplyMatchDAO viewerMatchApplyDAO;

	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private WinnerDAO winnerDAO;

	@Autowired
	private FilterService filterService;

	@Nullable
	public ResultVO apply(ViewerMatchApplyDTO applyFormListDTO) {
		if (applyFormListDTO.getApplyViewerFormList() == null) {
			return null;
		}

		MatchInfoVO matchInfoVO = matchInfoDAO.getCreateRecentMatchInfo(applyFormListDTO.getManagerName());

		if (!isMatchExsist(matchInfoVO)) {
			throw new RuntimeException("No such MatchInfo \n name :" + applyFormListDTO.getManagerName());
		}

		//서버에서 현재 신청한 시각을 구함
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

		//이벤트 시작시간이 아닌지 체크
		if (currentTime.isBefore(matchInfoVO.getStartTime()) ||
				currentTime.isAfter(matchInfoVO.getEndTime())) {
			throw new BusinessException("BeforeApply", "apply before start time");
		}

		List<ApplyFormDTO> applyViewerList = applyFormListDTO.getApplyViewerFormList();

		//중복신청한 유저가 있는지 체크
		for (ApplyFormDTO applyFormDTO : applyViewerList) {
			//신청자 정보들을 DB에 저장
			insertApplyForm(applyFormDTO, matchInfoVO);
		}

		ResultVO resultVO = new ResultVO();
		resultVO.setKey("Success");

		return resultVO;
	}

	@NotNull
	public List<String> lottery(StartLotteryDTO startLotteryDTO) {

		//당첨자 수 정하기
		int winCount = startLotteryDTO.getWinCount();

		//이벤트 Info 로드
		String creator = startLotteryDTO.getCreator();

		MatchInfoVO matchInfoVO = matchInfoDAO.getCreateRecentMatchInfo(creator);

		if (matchInfoVO == null) {
			throw new BusinessException("MatchInfo", "No Such Match Info");
		}

		List<ApplyTwitchUserVO> applyUserList = viewerMatchApplyDAO.getApplyList(matchInfoVO);

		if (applyUserList.isEmpty()) {
			throw new BusinessException("LotteryFail", "No applier In ViewerMatch : " + matchInfoVO.getPK());
		}

		List<ApplyTwitchUserVO> emailFilterUserList = filterService.filterDuplicatedPlayer(applyUserList);
		applyUserList = filterService.filterDuplicatedEmail(emailFilterUserList);

		List<ApplyTwitchUserVO> winnerList = lotteryService.lotteryByViewerMatch(applyUserList, winCount);

		//이메일 리스트를 받고 당첨자 정보를 저장함
		List<String> winnerEmailList = winnerList.stream()
				.peek(applyTwitchUserVO -> saveWinnerInfo(matchInfoVO.getPK(), applyTwitchUserVO))
				.map(this::getApplyEamilAddress)
				.collect(Collectors.toList());

		//당참자들에게 발송할 Discord 링크 리스트들을 받음
		List<String> discordInviteLinkList = LinkGenerator.getInstance().makeInviteUrlList(winnerEmailList.size());

		//당첨 이메일 을 발송함
		emailService.sendWinnerEmailwithInviteLink(matchInfoVO.getCreator(), winnerEmailList, discordInviteLinkList);

		return winnerEmailList;
	}

	public Integer getApplyCount(String creator) {
		MatchInfoVO matchInfoVO = matchInfoDAO.getCreateRecentMatchInfo(creator);

		if (matchInfoVO == null) {
			throw new BusinessException("creator", "can not find viewerMatchInfo");
		}

		List<ApplyTwitchUserVO> applyTwitchUserVOList = viewerMatchApplyDAO.getApplyList(matchInfoVO);

		return applyTwitchUserVOList.size();
	}

	@Async
	void insertApplyForm(ApplyFormDTO applyFormDTO, MatchInfoVO matchInfoVO) {
		if (viewerMatchApplyDAO.insert(applyFormDTO, matchInfoVO) < 0){
			throw new RuntimeException("Save Viewer Apply From Fail");
		}
	}

	private void saveWinnerInfo(int matchInfoPK, ApplyTwitchUserVO applyTwitchUserVO) {
		winnerDAO.insertWinner(matchInfoPK, applyTwitchUserVO.getPK());
	}

	private String getApplyEamilAddress(ApplyTwitchUserVO applyTwitchUserVO) {
		return applyTwitchUserVO.getEmail();
	}

	private boolean isMatchExsist(MatchInfoVO matchInfoVO) {
		return matchInfoVO != null;
	}
}
