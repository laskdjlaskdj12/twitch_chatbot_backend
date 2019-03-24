package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.ApplyHistoryDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.EmailDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.Error.BusinessException;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class LotteryService {

	@Autowired
	private ApplyHistoryDAO applyHistoryDAO;

	@Autowired
	private EmailDAO emailDAO;


	public List<ApplyVO> lotteryByViewerMatch(MatchInfoVO matchInfoVO, int winCount) {

		List<ApplyVO> applyList = applyHistoryDAO.getApplyHistory(matchInfoVO);

		if(applyList.isEmpty()){
			throw new BusinessException("ApplyList", "No Such ApplyList");
		}

		shuffleApplyList(applyList);

		List<ApplyVO> winnerList = new ArrayList<>();

		for (int i = 0; i < winCount; i++){
			int winnerNumber = makeRandomWinnerNumber(applyList.size());

			ApplyVO winnerApply = applyList.get(winnerNumber);

			//Apply가 Valid한지 확인
			if(!isValidApply(winnerApply)){
				continue;
			}

			//당첨 리스트에 넣음
			winnerList.add(winnerApply);

			//당첨자를 대기열에서 제외
			applyList.remove(winnerNumber);
		}

		return winnerList;
	}

	public boolean isValidApply(ApplyVO winnerApply) {
		Integer emailPK = winnerApply.getEmailPK();

		EmailVO emailVO = emailDAO.getEmailByPK(emailPK);

		//이메일이 있는지 확인
		return !emailVO.getEmail().isEmpty();
	}

	private int makeRandomWinnerNumber(int applySize) {
		long seed = System.nanoTime();
		return (int) (seed % applySize);
	}

	private void shuffleApplyList(List<ApplyVO> applyVOS){
		long seed = System.nanoTime();
		Collections.shuffle(applyVOS, new Random(seed));
	}
}
