package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.MatchInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.MatchInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.Error.BusinessException;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ResultVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchInfoService {

	@Autowired
	private MatchInfoDAO matchInfoDAO;

	public ResultVO createMatch(MatchInfoDTO matchInfoDTO) {
		LocalDateTime startTime = Utils.stringToLocalDateTime(matchInfoDTO.getStartTime());
		LocalDateTime endTime = Utils.stringToLocalDateTime(matchInfoDTO.getEndTime());

		Integer integer = matchInfoDAO.insert(matchInfoDTO.getCreator(), startTime, endTime);

		if(integer < 0){
			throw new BusinessException("Match", "Save Viewer Match Info Fail");
		}

		ResultVO resultVO = new ResultVO();
		resultVO.setKey("Success");
		resultVO.setMessage("make Viewer Match Complete");

		return resultVO;
	}
}
