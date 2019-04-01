package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.MatchInfoDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.StartLotteryDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ViewerMatchApplyDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ResultVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewerMatchServiceTest {

	@Autowired
	private MatchInfoDAO matchInfoDAO;

	@Autowired
	ViewerMatchService viewerMatchService;

	/**
	 * Warning : 이 테스트는 DB에 Mock Data 값이 저장되기때문에 Before를 실행할때 테스트 DB들을 Flush하고 사용할것
	 */

//	@Before
//	public void before(){
//		LocalDateTime applyAllowStartTime = LocalDateTime.now();
//		LocalDateTime applyAllowEndTime = LocalDateTime.now().plusDays(10);
//
//		//매치정보를 등록함
//		matchInfoDAO.insert("laskdj", applyAllowStartTime, applyAllowEndTime);
//	}

	@Test
	public void applyTest(){
		long start = System.currentTimeMillis();
		ViewerMatchApplyDTO viewerMatchApplyDTO = makeViewerMatchApplyDTOMock();
		ResultVO resultVO = viewerMatchService.apply(viewerMatchApplyDTO);
		long end = System.currentTimeMillis();

		double gap = ((end - start) / 1000.0);

		Assert.assertNotNull(resultVO);
		System.out.println("걸린시간 : " + gap + "초");
	}

	@Test
	public void lotteryTest(){
		Integer winnerCount = 10;
		StartLotteryDTO startLotteryDTO = new StartLotteryDTO();
		startLotteryDTO.setCreator("laskdj");
		startLotteryDTO.setWinCount(winnerCount);

		List<String> emailList = viewerMatchService.lottery(startLotteryDTO);

		boolean is_not_empty_true = !emailList.isEmpty();
		boolean is_winner_is_10_true = emailList.size() == 2;

		Assert.assertTrue(is_not_empty_true);
		Assert.assertTrue(is_winner_is_10_true);

		emailList.stream()
				.peek(emailVO -> System.out.println(emailVO))
				.collect(Collectors.toList());
	}

	private ViewerMatchApplyDTO makeViewerMatchApplyDTOMock() {
		ViewerMatchApplyDTO viewerMatchApplyDTO = new ViewerMatchApplyDTO();
		viewerMatchApplyDTO.setManagerName("laskdj");

		List<ApplyFormDTO> applyFormDTOS = new ArrayList<>();

		for (int i = 0; i < 1000; i++){
			UserInfoDTO userInfoDTO = makeUserInfoDTOMock(i);

			ApplyFormDTO applyFormDTO = new ApplyFormDTO();
			applyFormDTO.setEmail("laskdj" + i + "@gmail.com");
			applyFormDTO.setUserInfoDTO(userInfoDTO);

			applyFormDTOS.add(applyFormDTO);
		}

		viewerMatchApplyDTO.setApplyViewerFormList(applyFormDTOS);

		return viewerMatchApplyDTO;
	}

	private UserInfoDTO makeUserInfoDTOMock(int index) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();

		userInfoDTO.setName("laskdj" + index);
		userInfoDTO.setId((long)index);
		return userInfoDTO;
	}
}
