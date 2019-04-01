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
		stringBuilder.append("시청자님께서는 아래 디스코드 채널 에 참가를 해주시면 됩니다. \n");
		stringBuilder.append("디스코드 초대 URL : " + discordUrl + "\n");
		stringBuilder.append("진행 특성상 빠른 진행을 위해 5분이내로 참여를 하셔야합니다.\n\n");
		stringBuilder.append("이 시참 시스템은 알파버전이여서 시스템 불안정이 있을수 있으니 많은 양해 부탁드립니다.\n\n");
		stringBuilder.append("만약 이상이 발생하셨다면 viewerMatcher@google.com 으로 메일 부탁드립니다.");
		return stringBuilder.toString();
	}
}
