package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.List;

public class LinkService {

	private JDA jda;

	public LinkService(JDA jda){
		this.jda = jda;
	}

	public List<String> makeInviteLinkByCount(int count, String voiceChannelName){
		VoiceChannel voiceChannel = jda.getVoiceChannelByName(voiceChannelName, true).get(0);

		return makeInviteLinkByCount(count, voiceChannel.getIdLong());
	}

	public List<String> makeInviteLinkByCount(int count, Long voiceChannelID){
		VoiceChannel voiceChannel = jda.getVoiceChannelById(voiceChannelID);

		List<String> inviteUrlList = new ArrayList<>();

		for(int i = 0; i < count; i++){
			String inviteUrl = voiceChannel.createInvite().setMaxAge(300).complete().getUrl();
			inviteUrlList.add(inviteUrl);
		}

		return inviteUrlList;
	}
}
