package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewerApplyMatchDAOTest {

	@Autowired
	ViewerApplyMatchDAO viewerApplyMatchDAO;

	@Test
	public void insertTest(){
		ApplyFormDTO applyFormDTO = makeApplyFormDTOMock();
		MatchInfoVO matchInfoVO = makeMatchInfoVOMock();

		int result = viewerApplyMatchDAO.insert(applyFormDTO, matchInfoVO);

		boolean is_result_over_0 = result > 0;
		Assert.assertTrue(is_result_over_0);
	}

	private MatchInfoVO makeMatchInfoVOMock() {
		MatchInfoVO matchInfoVO = new MatchInfoVO();
		matchInfoVO.setPK(10);

		return matchInfoVO;
	}

	private ApplyFormDTO makeApplyFormDTOMock() {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setId((long)20150840);
		userInfoDTO.setName("laskdjlaskdj12");

		ApplyFormDTO applyFormDTO = new ApplyFormDTO();
		applyFormDTO.setEmail("laskdjlaskdj12@gmail.com");
		applyFormDTO.setUserInfoDTO(userInfoDTO);

		return applyFormDTO;
	}

	@Test
	public void getApplyListTest(){
		MatchInfoVO matchInfoVO = makeMatchInfoVOMock();
		List<ApplyTwitchUserVO> applyTwitchUserVOS = viewerApplyMatchDAO.getApplyList(matchInfoVO);

		boolean is_not_empty_true = !applyTwitchUserVOS.isEmpty();

		Assert.assertTrue(is_not_empty_true);
	}

	@Test
	public void getApplyByEmailTest(){
		List<ApplyTwitchUserVO> applyTwitchUserVOS = viewerApplyMatchDAO.getApplyByEmail("laskdjlaskdj12@gmail.com");

		boolean is_not_empty_true = !applyTwitchUserVOS.isEmpty();

		Assert.assertTrue(is_not_empty_true);
	}

	@Test
	public void getApplyByViewerIdTest(){
		List<ApplyTwitchUserVO> applyTwitchUserVOS = viewerApplyMatchDAO.getApplyByViewerID((long)20150840);

		boolean is_not_empty_true = !applyTwitchUserVOS.isEmpty();

		Assert.assertTrue(is_not_empty_true);
	}

}
