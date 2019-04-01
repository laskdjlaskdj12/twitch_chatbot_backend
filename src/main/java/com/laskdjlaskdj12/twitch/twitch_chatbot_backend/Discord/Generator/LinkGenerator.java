package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Generator;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService.LinkService;

import java.util.List;

public class LinkGenerator {

	private LinkService linkService;

	private static LinkGenerator linkGenerator;

	private LinkGenerator(){}

	public static LinkGenerator getInstance(){
		if(linkGenerator == null){
			linkGenerator = new LinkGenerator();
		}

		return linkGenerator;
	}

	public void setLinkService(LinkService linkService){
		this.linkService = linkService;
	}

	public List<String> makeInviteUrlList(int count){
		return this.linkService.makeInviteLinkByCount(count, "General");
	}
}
