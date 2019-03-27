package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Deprecated
@Data
public class EmailVO {

	@NotNull
	private Integer PK;

	@NotNull
	private String email;

	@NotNull
	private Integer userPK;
}
