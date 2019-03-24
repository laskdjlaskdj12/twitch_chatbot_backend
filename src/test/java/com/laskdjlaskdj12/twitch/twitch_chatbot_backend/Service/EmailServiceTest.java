package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

	@Autowired
	EmailService lotteryMailService;

	@Test
	public void sendEmailTest(){
		EmailVO emailVO = makeEmailMock();
		String formString = "이것은 이메일입니다";
		lotteryMailService.sendEmail(emailVO, formString);
	}

	private EmailVO makeEmailMock() {
		EmailVO emailVO = new EmailVO();

		emailVO.setPK(10);
		emailVO.setEmail("laskdjlaskdj@naver.com");
		emailVO.setUserPK(10);

		return emailVO;
	}
}
