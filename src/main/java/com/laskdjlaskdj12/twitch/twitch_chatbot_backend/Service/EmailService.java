package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

	@Autowired
	JavaMailSender emailSender;

	public void sendWinnerEmail(List<String> winnerEmailList) {

		String emailForm = makeWinnerEmailForm();
		for (String email : winnerEmailList){
			sendEmail(email, emailForm);
		}
	}

	private String makeWinnerEmailForm() {
		return "이건 테스트 이메일입니다.";
	}

	//당첨 이메일을 전송함
	public void sendEmail(String email, String emailForm){
		//해당 이메일에 전송함
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("laskdj님의 시청자 참여에 당첨이 되셨습니다.");
		message.setText(emailForm);
		emailSender.send(message);
	}
}
