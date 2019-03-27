package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.*;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.StartLotteryDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ViewerMatchApplyDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.Error.BusinessException;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

	@Nullable
	public ResultVO apply(ViewerMatchApplyDTO applyFormListDTO) {
		if(applyFormListDTO.getApplyViewerFormList() == null){
			return null;
		}

		//이벤트 시작시간이 아닐경우
		MatchInfoVO matchInfoVO = matchInfoDAO.getMatchByCreator(applyFormListDTO.getManagerName());

		if(!isMatchExsist(matchInfoVO)){
			throw new RuntimeException("No such MatchInfo \n name :" + applyFormListDTO.getManagerName());
		}

		LocalDateTime currentTime = LocalDateTime.now();

		if(currentTime.isBefore(matchInfoVO.getStartTime()) ||
		currentTime.isAfter(matchInfoVO.getEndTime())){
			throw new BusinessException("BeforeApply", "apply before start time");
		}

		List<ApplyFormDTO> applyViewerList = applyFormListDTO.getApplyViewerFormList();

		for (ApplyFormDTO applyFormDTO: applyViewerList) {

			//중복신청한 유저가있는지 체크
			List<ApplyTwitchUserVO> applierList = viewerMatchApplyDAO.getApplyByViewerID(applyFormDTO.getUserInfoDTO().getId());

			if(!applierList.isEmpty()){
				continue;
			}

			//신청한 이메일이 있는지 체크
			List<ApplyTwitchUserVO> duplicateApplier = viewerMatchApplyDAO.getApplyByEmail(applyFormDTO.getEmail());

			if(!duplicateApplier.isEmpty()){
				continue;
			}

			//신청자 정보들을 DB에 저장
			viewerMatchApplyDAO.insert(applyFormDTO, matchInfoVO);
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

		MatchInfoVO matchInfoVO = matchInfoDAO.getMatchByCreator(creator);

		if(matchInfoVO == null){
			throw new BusinessException("MatchInfo", "No Such Match Info");
		}

		List<ApplyTwitchUserVO> applyUserList = viewerMatchApplyDAO.getApplyList(matchInfoVO);

		List<ApplyTwitchUserVO> winnerList = lotteryService.lotteryByViewerMatch(applyUserList, winCount);

		//이메일 리스트를 받고 당첨자 정보를 저장함
		List<String> winnerEmailList = winnerList.stream()
				.peek(applyTwitchUserVO -> saveWinnerInfo(matchInfoVO.getPK(), applyTwitchUserVO))
				.map(this::getApplyEamilAddress)
				.collect(Collectors.toList());

		//당첨 이메일 을 발송함
		emailService.sendWinnerEmail(winnerEmailList);

		return winnerEmailList;
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
