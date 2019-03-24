package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailDAOTest {

	@Autowired
	private EmailDAO emailDAO;

	@Test
	public void insertTest(){
		UserInfoVO userInfoVO = makeUserInfoMock();
		String email = "laskdjlaskdj12@gmail.com";

		int PK = emailDAO.insert(userInfoVO, email);

		Assert.assertTrue(PK > 0);

		System.out.println("PK : " + PK);
	}

	@Test
	public void getEmailByUserPK(){
		EmailVO emailVO = emailDAO.getEmailByUserPK(0);

		Assert.assertNotNull(emailVO);
	}

	@Test
	public void getUserByEmail(){
		List<EmailVO> emailVO = emailDAO.getUserByEmail("laskdjlaskdj12@gmail.com");

		Assert.assertFalse(emailVO.isEmpty());
		Assert.assertNotNull(emailVO.get(0));
	}

	@Test
	public void getEmailByPKTest(){
		EmailVO emailVO = emailDAO.getEmailByPK(1);
		boolean is_emailVO_not_null_true = emailVO != null;
		
		Assert.assertTrue(is_emailVO_not_null_true);
	}

	private UserInfoVO makeUserInfoMock() {
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setId((long)0102301);
		userInfoVO.setName("laskdjlaskdj12");

		return userInfoVO;
	}


}
