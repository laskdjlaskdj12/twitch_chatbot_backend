package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class LotteryService {
	public List<ApplyTwitchUserVO> lotteryByViewerMatch(List<ApplyTwitchUserVO> applyUserList, int winCount) {

		//신청자 리스트의 순서를 섞음
		shuffleApplyList(applyUserList);

		List<ApplyTwitchUserVO> winnerList = new ArrayList<>();

		for (int i = 0; i < winCount; i++){
			int winnerNumber = makeRandomWinnerNumber(applyUserList.size());

			ApplyTwitchUserVO winnerApply = applyUserList.get(winnerNumber);

			//당첨 리스트에 넣음
			winnerList.add(winnerApply);

			//당첨자를 신청자리스트에서 제외
			applyUserList.remove(winnerNumber);
		}

		return winnerList;
	}

	private int makeRandomWinnerNumber(int applySize) {
		long seed = System.nanoTime();
		return (int) (seed % applySize);
	}

	private void shuffleApplyList(List<ApplyTwitchUserVO> applyVOS){
		long seed = System.nanoTime();
		Collections.shuffle(applyVOS, new Random(seed));
	}
}
