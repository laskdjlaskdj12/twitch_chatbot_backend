package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService;

import org.junit.Assert;
import org.junit.Test;

public class PropertyServiceTest {

	private PropertyService propertyService = new PropertyService();

	@Test
	public void propertyServiceTest(){
		String tokenString = propertyService.getPorperty("discordBotToken");

		boolean is_token_is_not_empty = !tokenString.isEmpty();
		Assert.assertTrue(is_token_is_not_empty);
	}
}
