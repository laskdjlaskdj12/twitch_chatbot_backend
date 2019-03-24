package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StartLotteryDTO {

	@NotNull
	Integer PK;

	@NotNull
	Integer winCount;

	@NotNull
	String creator;
}
