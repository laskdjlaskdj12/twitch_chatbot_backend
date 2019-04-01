package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Event.CommandEvent;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Event.InviteEvent;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Generator.LinkGenerator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.util.List;

public class LinkServiceTest {

	private JDA jda;

	private LinkService linkService;

	@Before
	public void before() throws LoginException, InterruptedException {
		jda = makeJDA();
		linkService = new LinkService(this.jda);
	}

	@Test
	public void generateLinktest() {
		List<String> urlList = linkService.makeInviteLinkByCount(5, "General");

		boolean is_url_list_not_empty = !urlList.isEmpty();

		Assert.assertTrue(is_url_list_not_empty);
	}

	private JDA makeJDA() throws LoginException, InterruptedException {
		PropertyService propertyService = new PropertyService();
		String discordBotToken = propertyService.getPorperty("discordBotToken");

		// Note: It is important to register your ReadyListener before building
		JDA jda = null;
		LinkService linkService = null;
		jda = new JDABuilder(discordBotToken)
				.setEventManager(new AnnotatedEventManager())
				.addEventListeners(new CommandEvent())
				.addEventListeners(new InviteEvent())
				.build();

		linkService = new LinkService(jda);
		LinkGenerator.getInstance().setLinkService(linkService);

		jda.awaitReady();

		return jda;
	}
}
