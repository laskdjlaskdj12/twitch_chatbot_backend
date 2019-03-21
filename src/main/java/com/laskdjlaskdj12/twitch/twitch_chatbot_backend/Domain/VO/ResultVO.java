package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO;

import lombok.Data;

@Data
public class ResultVO implements Reply{
	private String message;
	private String key;
}
