package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterServiceTest {

	@Autowired
	FilterService filterService;

	private List<ApplyTwitchUserVO> applyTwitchUserVOMockList;

	@Before
	public void before() {
		applyTwitchUserVOMockList = new ArrayList<>();

		applyTwitchUserVOMockList = makeTestApplyMockList();
	}

	@Test
	public void filterDuplicatedEmailTest() {
		for (ApplyTwitchUserVO applyTwitchUserVO : applyTwitchUserVOMockList) {
			System.out.println(applyTwitchUserVO);
		}

		List<ApplyTwitchUserVO> applyTwitchUserVOS = filterService.filterDuplicatedEmail(applyTwitchUserVOMockList);

		boolean is_filtered_list_is_five = applyTwitchUserVOS.size() == 500;

		Assert.assertTrue(is_filtered_list_is_five);
	}

	@Test
	public void filterDuplicatedPlayerTest(){
		for (ApplyTwitchUserVO applyTwitchUserVO : applyTwitchUserVOMockList) {
			System.out.println(applyTwitchUserVO);
		}

		List<ApplyTwitchUserVO> applyTwitchUserVOS = filterService.filterDuplicatedPlayer(applyTwitchUserVOMockList);

		boolean is_filtered_list_is_five = applyTwitchUserVOS.size() == 500;

		Assert.assertTrue(is_filtered_list_is_five);
	}

	@Test
	public void allFilterPlyaerTest(){
		List<ApplyTwitchUserVO> emailFilteredList = filterService.filterDuplicatedEmail(applyTwitchUserVOMockList);
		List<ApplyTwitchUserVO> playerFilteredList = filterService.filterDuplicatedPlayer(emailFilteredList);

		boolean is_player_filteredList_is_500 = playerFilteredList.size() == 500;

		Assert.assertTrue(is_player_filteredList_is_500);
	}

	private List<ApplyTwitchUserVO> makeTestApplyMockList() {
		List<ApplyTwitchUserVO> applyTwitchUserVOS = new ArrayList<>();

		int count = 1000;

		for (int i = 1; i < count; i++) {
			if (i % 2  == 0) {
				ApplyTwitchUserVO applyTwitchUserVO = makeMock(i, true);
				applyTwitchUserVOS.add(applyTwitchUserVO);
			} else {
				ApplyTwitchUserVO applyTwitchUserVO = makeMock(i, false);
				applyTwitchUserVOS.add(applyTwitchUserVO);
			}
		}

		return applyTwitchUserVOS;
	}

	private ApplyTwitchUserVO makeMock(int i, boolean isDuplicated) {
		if(isDuplicated){
			int index = i - 1;

			ApplyTwitchUserVO applyTwitchUserVO = new ApplyTwitchUserVO();
			applyTwitchUserVO.setPK(i);
			applyTwitchUserVO.setEmail("laskdjlaskdj" + index + "@gmail.com");
			applyTwitchUserVO.setName("laskdjlaskdj" + index);
			applyTwitchUserVO.setMatchInfoPK(24);
			applyTwitchUserVO.setId((long) index + 10000);

			return applyTwitchUserVO;
		}

		return makeMock(i);
	}

	private ApplyTwitchUserVO makeMock(int i) {
		ApplyTwitchUserVO applyTwitchUserVO = new ApplyTwitchUserVO();
		applyTwitchUserVO.setPK(i);
		applyTwitchUserVO.setEmail("laskdjlaskdj" + i + "@gmail.com");
		applyTwitchUserVO.setName("laskdjlaskdj" + i);
		applyTwitchUserVO.setMatchInfoPK(24);
		applyTwitchUserVO.setId((long) i + 10000);

		return applyTwitchUserVO;
	}
}
