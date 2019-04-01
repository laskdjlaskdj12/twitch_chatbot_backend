package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Utils.Form.EmailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

	@Autowired
	JavaMailSender emailSender;

	public void sendWinnerEmailwithInviteLink(String creator, List<String> winnerEmailList, List<String> discordInviteUrlList) {
		for (int i = 0; i < winnerEmailList.size(); i++){
			String email = winnerEmailList.get(i);
			String discordUrl = discordInviteUrlList.get(i);

			String emailForm = makeWinnerEmailForm(creator, discordUrl);
			sendEmail(creator, email, emailForm);
		}
	}

	private String makeWinnerEmailForm(String creator, String discordUrl) {
		return EmailForm.getInstance().getWinnerEmailForm(creator, discordUrl);
	}

	//당첨 이메일을 전송함
	public void sendEmail(String creator, String email, String emailForm){
		//해당 이메일에 전송함
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(creator + "의 시청자 참여에 당첨이 되셨습니다.");
		message.setText(emailForm);
		emailSender.send(message);
	}
}
