package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplyFormDTO {

	@NotNull
	private UserInfoDTO userInfoDTO;

	@NotNull
	private String email;
}
