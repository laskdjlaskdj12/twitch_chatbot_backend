package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO;

import lombok.Data;

import javax.validation.constraints.NotNull;

//Todo: ApplyVO의 명칭이 헷갈려서 ApplyVO를 없애기
@Data
public class ApplyTwitchUserVO {

	@NotNull
	private Integer PK;

	@NotNull
	private String email;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private Integer matchInfoPK;
}
