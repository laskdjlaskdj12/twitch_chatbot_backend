package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService.LinkService;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService.PropertyService;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Event.CommandEvent;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Event.InviteEvent;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Generator.LinkGenerator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

@Component
public class DiscordBot {

	@PostConstruct
	public void init() {
		Logger logger = Logger.getLogger(DiscordBot.class.getName());

		//프로퍼티를 읽음
		logger.info("Load Config file...");
		PropertyService propertyService = new PropertyService();
		String discordBotToken = propertyService.getPorperty("discordBotToken");

		// Note: It is important to register your ReadyListener before building
		JDA jda = null;
		LinkService linkService = null;
		try {
			jda = new JDABuilder(discordBotToken)
					.setEventManager(new AnnotatedEventManager())
					.addEventListeners(new CommandEvent())
					.addEventListeners(new InviteEvent())
					.build();

			linkService = new LinkService(jda);
			LinkGenerator.getInstance().setLinkService(linkService);

			jda.awaitReady();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
