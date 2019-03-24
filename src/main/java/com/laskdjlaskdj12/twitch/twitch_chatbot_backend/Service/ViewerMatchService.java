package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.EmailDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.MatchInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.UserInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.ApplyHistoryDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.StartLotteryDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
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
	private UserInfoDAO userInfoDAO;

	@Autowired
	private MatchInfoDAO matchInfoDAO;

	@Autowired
	private EmailDAO emailDAO;

	@Autowired
	private ApplyHistoryDAO viewerApplyMatchHistoryDAO;

	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private LotteryMailService emailService;

	@Nullable
	public ResultVO apply(ViewerMatchApplyDTO applyFormListDTO) {
		if(applyFormListDTO.getApplyViewerFormList() == null){
			return null;
		}

		//신청자들이 신청한 매치정보의 시간대가 아닐경우
		MatchInfoVO matchInfoVO = matchInfoDAO.getByCreator(applyFormListDTO.getManagerName());

		if(!isMatchExsist(matchInfoVO)){
			throw new RuntimeException("No Such Business Exception \n name :" + applyFormListDTO.getManagerName());
		}

		LocalDateTime currentTime = LocalDateTime.now();

		if(currentTime.isBefore(matchInfoVO.getStartTime())){
			throw new BusinessException("BeforeApply", "apply before start time");
		}

		//신청자들을 받음
		for (ApplyFormDTO applyFormDTO: applyFormListDTO.getApplyViewerFormList()) {

			//신청자들의 User정보를 DB에 저장함
			UserInfoVO userInfoVO = userInfoDAO.getUserByID(applyFormDTO.getUserInfoDTO().getId());

			if(!isUserInfoExsist(userInfoVO)){
				//userInfo를 저장함
				UserInfoDTO userInfoDTO = applyFormDTO.getUserInfoDTO();

				userInfoDAO.addUser(userInfoDTO);

				userInfoVO = userInfoDAO.getUserByID(userInfoDTO.getId());
			}

			//유저가 중복으로 신청을 했는지 체크
			EmailVO applyEmail = emailDAO.getEmailByUserPK(userInfoVO.getPK());

			//이미 신청한 이메일이 있으면 무시하고 넘어감
			if(isEmailExsist(applyEmail)){
				continue;
			}

			//신청한 이메일이 다른사람이 쓰고있는 이메일인지 확인
			List<EmailVO> emailUserPKList = emailDAO.getUserByEmail(applyFormDTO.getEmail());

			//만약 이메일이 이미 등록이 되어 있을경우에 continue
			if(!emailUserPKList.isEmpty()){
				continue;
			}

			//이메일을 등록함
			Integer emailVOPK = emailDAO.insert(userInfoVO, applyFormDTO.getEmail());

			//History에 저장함
			LocalDateTime currentDateTime = LocalDateTime.now();
			Integer applyMatchVOPK = viewerApplyMatchHistoryDAO.addHistory(userInfoVO, emailVOPK, matchInfoVO, currentDateTime);

			if(isInsertHistoryFail(applyMatchVOPK)){
				throw new RuntimeException("Can't insert runtime exception");
			}
		}

		ResultVO resultVO = new ResultVO();
		resultVO.setKey("Success");

		return resultVO;
	}

	@NotNull
	public List<EmailVO> lottery(StartLotteryDTO startLotteryDTO) {

		//몇명을 추첨하는지 체크
		int winCount = startLotteryDTO.getWinCount();

		//MatchInfo 히스토리를 갖고 옴
		String creator = startLotteryDTO.getCreator();

		MatchInfoVO matchInfoVO = matchInfoDAO.getByCreator(creator);

		//응모한 유저 들중에서 확률을 정해서 추첨을 시작함
		List<ApplyVO> winnerList = lotteryService.lotteryByViewerMatch(matchInfoVO, winCount);

		List<EmailVO> winnerEmailList = winnerList.stream()
				.map(applyVO -> getApplyEamilAddress(applyVO))
				.collect(Collectors.toList());

		//이메일 을 발송함
		emailService.sendWinnerEmail(winnerEmailList);

		return winnerEmailList;
	}

	private EmailVO getApplyEamilAddress(ApplyVO applyVO) {
		return emailDAO.getEmailByUserPK(applyVO.getUserPK());
	}

	private boolean isInsertHistoryFail(Integer applyMatchVOPK) {
		return applyMatchVOPK <= 0;
	}

	private boolean isEmailExsist(EmailVO applyEmail) {
		return applyEmail != null;
	}

	private boolean isUserInfoExsist(UserInfoVO userInfoVO) {
		return userInfoVO != null;
	}

	private boolean isMatchExsist(MatchInfoVO matchInfoVO) {
		return matchInfoVO != null;
	}
}
