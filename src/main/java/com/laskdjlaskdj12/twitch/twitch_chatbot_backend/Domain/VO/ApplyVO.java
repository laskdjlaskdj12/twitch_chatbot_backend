package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ApplyVO {

	@NotNull
	private Integer PK;

	@NotNull
	private Integer userPK;

	@NotNull
	private Integer emailPK;

	@NotNull
	private Integer matchInfoPK;

	@NotNull
	private LocalDateTime currentTime;
}
