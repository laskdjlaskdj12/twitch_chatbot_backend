package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class MatchInfoVO {
	private int PK;

	private String creator;

	@NotNull
	private LocalDateTime startTime;

	@NotNull
	private LocalDateTime endTime;
}
