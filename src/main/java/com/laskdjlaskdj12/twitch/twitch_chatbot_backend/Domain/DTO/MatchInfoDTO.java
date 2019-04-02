package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
public class MatchInfoDTO {

	@NotNull
	String creator;

	@NotNull
	LocalDateTime startTime;

	@NotNull
	LocalDateTime endTime;
}
