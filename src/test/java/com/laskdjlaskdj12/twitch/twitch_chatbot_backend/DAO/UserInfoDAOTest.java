package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoDAOTest {

	@Autowired
	UserInfoDAO userInfoDAO;

	@Test
	public void addUser(){
		UserInfoDTO userInfoDTO = makeUserInfoDTOMock();

		int insertPK = userInfoDAO.addUser(userInfoDTO);

		Assert.assertTrue(insertPK > 0);
	}

	@Test
	public void getUserByIDTest(){
		UserInfoVO userInfoVO = userInfoDAO.getUserByID(12);

		boolean is_userInfoVo_not_empty_true = userInfoVO != null;
		Assert.assertTrue(is_userInfoVo_not_empty_true);
	}

	private UserInfoDTO makeUserInfoDTOMock() {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setId((long)12);
		userInfoDTO.setName("laskdjlaskdj12");

		return userInfoDTO;
	}
}
