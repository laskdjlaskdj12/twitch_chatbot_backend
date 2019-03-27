package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {

	@Autowired
	private LotteryService lotteryService;

	@Test
	public void lotteryByViewerMatchTest(){
		List<ApplyTwitchUserVO> applyTwitchUserVOS = makeApplyTwitchUserMock();
		List<ApplyTwitchUserVO> winnerList = lotteryService.lotteryByViewerMatch(applyTwitchUserVOS, 4);

		boolean is_winnerList_not_empty_true = !winnerList.isEmpty();
		boolean is_winner_size_is_4_true = winnerList.size() == 4;

		Assert.assertTrue(is_winnerList_not_empty_true);
		Assert.assertTrue(is_winner_size_is_4_true);

		for (ApplyTwitchUserVO applyTwitchUserVO: winnerList){
			System.out.println(applyTwitchUserVO);
		}
	}

	private List<ApplyTwitchUserVO> makeApplyTwitchUserMock() {

		List<ApplyTwitchUserVO> applyTwitchUserVOS = new ArrayList<>();

		for (int i = 0; i < 1000; i++){
			ApplyTwitchUserVO applyTwitchUserVO = new ApplyTwitchUserVO();
			applyTwitchUserVO.setMatchInfoPK(10);
			applyTwitchUserVO.setName("laskdj" + i);
			applyTwitchUserVO.setId((long)(2015 + i));
			applyTwitchUserVO.setEmail("laskdj" + i + "@gmail.com");
			applyTwitchUserVO.setPK(i);
			applyTwitchUserVOS.add(applyTwitchUserVO);
		}

		return applyTwitchUserVOS;
	}
}
