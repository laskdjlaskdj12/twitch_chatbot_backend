package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Utils.Form;

public class EmailForm {

	private static EmailForm emailForm;

	private EmailForm(){}

	public static EmailForm getInstance(){
		if(emailForm == null){
			emailForm = new EmailForm();
		}

		return emailForm;
	}

	public String getWinnerEmailForm(String creator, String discordUrl){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("안녕하세요 시청자님 \n");
		stringBuilder.append(creator + "의 시청자 참여 컨텐츠에 당첨이 되셨습니다. \n");
		stringBuilder.append("시청자님께서는 아래 discordChannel에 참가를 해주시면 됩니다. \n");
		stringBuilder.append(discordUrl);
		return stringBuilder.toString();
	}
}
