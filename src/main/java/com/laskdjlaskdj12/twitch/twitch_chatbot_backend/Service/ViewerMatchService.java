package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.EmailDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.MatchInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.UserInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.ViewerApplyMatchHistoryDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ViewerMatchApplyDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.Error.BusinessException;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ResultVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViewerMatchService {

	@Autowired
	private UserInfoDAO userInfoDAO;

	@Autowired
	private MatchInfoDAO matchInfoDAO;

	@Autowired
	private EmailDAO emailDAO;

	@Autowired
	private ViewerApplyMatchHistoryDAO viewerApplyMatchHistoryDAO;

	@Nullable
	public ResultVO apply(ViewerMatchApplyDTO applyFormListDTO) {
		if(applyFormListDTO.getApplyViewerFormList() == null){
			return null;
		}

		//신청자들이 신청한 매치정보의 시간대가 아닐경우
		MatchInfoVO matchInfoVO = matchInfoDAO.getMatchInfoByCreator(applyFormListDTO.getManagerName());

		if(matchInfoVO == null){
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

			if(userInfoVO == null){
				//userInfo를 저장함
				UserInfoDTO userInfoDTO = applyFormDTO.getUserInfoDTO();

				userInfoDAO.addUser(userInfoDTO);

				userInfoVO = userInfoDAO.getUserByID(userInfoDTO.getId());
			}

			//신청한 이메일이 있는지 확인
			EmailVO appliedEmail = emailDAO.getEmailByUserPK(userInfoVO.getPK());

			//이메일이 이미 있으면 저장하지 않음
			if(appliedEmail != null){
				continue;
			}

			//신청자가 한 이메일이 다른사람이 쓰고있는 이메일인지 확인
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

			if(applyMatchVOPK <= 0){
				throw new RuntimeException("Can't insert runtime exception");
			}
		}

		ResultVO resultVO = new ResultVO();
		resultVO.setKey("Success");

		return resultVO;
	}
}
