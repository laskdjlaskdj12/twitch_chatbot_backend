package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.Event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.managers.GuildController;

import java.awt.*;

public class CommandEvent {

	@SubscribeEvent
	public void onCommandEvent(GuildMessageReceivedEvent e) {

		String command = e.getMessage().getContentRaw();
		//명령어가 오는지 확인
		if(command.equals("!message")){
			e.getChannel().sendMessage("안녕하세요 메세지입니다.").queue();
		}

		//플레이어의 대화채널을 옮기는 명령어일경우
		if(command.equals("!move")){

			e.getChannel().sendMessage("채널들을 이동합니다.").queue();

			GuildController guildController = new GuildController(e.getGuild());

			//플레이어가 있는 대화채널을들을 전부 파싱함
			VoiceChannel generalVoiceChannel = e.getGuild().getVoiceChannelsByName("General", true).get(0);
			VoiceChannel tutorialVoiceChannel = e.getGuild().getVoiceChannelsByName("Tutorial", true).get(0);

			//플레이어가 있는 대화채널들을 전부 파싱함
			Member member = generalVoiceChannel.getMembers().get(0);
			guildController.moveVoiceMember(member, tutorialVoiceChannel).complete();
		}

		//임베디드 메세지명령어
		if(command.equals("!emessage")){
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Title", null);
			eb.setColor(Color.RED);
			eb.setDescription("이건 텍스트입니다.");
			eb.addBlankField(true);
			eb.addField("타이틀 필드", "이건 타이틀 필드입니다.", false);
			eb.addField("타이틀 필드", "이건 타이틀 필드입니다.", false);
			eb.addField("타이틀 필드", "이건 타이틀 필드입니다.", false);
			eb.addField("타이틀 필드", "이건 타이틀 필드입니다.", false);
			MessageEmbed messageEmbed = eb.build();

			e.getChannel().sendMessage(messageEmbed).queue();
		}

		//카운트다운 명령어
		if(command.equals("!countDown")){

			Message updateMessage = null;
			for (int i = 3; i > 0; i--){
				MessageEmbed messageEmbed = makeCountDownMessageEmbed(i);

				if(updateMessage == null) {
					updateMessage = e.getChannel().sendMessage(messageEmbed).complete();
				}else {
					updateMessage.editMessage(messageEmbed).queue();
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			MessageEmbed messageEmbed = startGameMessageEmbededForm();
			updateMessage.editMessage(messageEmbed).complete();
		}

		//뮤트 명령어
		if(command.equals("!mute")){
			VoiceChannel generalVoiceChannel = e.getGuild().getVoiceChannelsByName("General", true).get(0);
			GuildController guildController = e.getGuild().getController();
			guildController.setMute(e.getMember(), true).complete();
			guildController.setNickname(e.getMember(), "Owner").complete();
			MessageEmbed messageEmbed = muteMessageEmbededForm(e.getMember());
			e.getChannel().sendMessage(messageEmbed).complete();
		}

		if(command.equals("!invite")){
			VoiceChannel generalVoiceChannel = e.getGuild().getVoiceChannelsByName("General", true).get(0);

			String url = generalVoiceChannel.createInvite().setMaxAge(300).complete().getUrl();

			MessageEmbed messageEmbed = makeInviteLinkMessageForm(url);
			e.getChannel().sendMessage(messageEmbed).complete();
		}
	}

	private MessageEmbed makeInviteLinkMessageForm(String url) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription("초대 URL : " + url);
		eb.setColor(Color.GREEN);
		return eb.build();
	}

	private MessageEmbed muteMessageEmbededForm(Member member) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(member.getNickname() + "님이 뮤트되셨습니다.");
		eb.setColor(Color.GREEN);
		return eb.build();
	}

	private MessageEmbed makeCountDownMessageEmbed(int second) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription("게임 시작되기 " + second + "초 전...");
		eb.setColor(Color.BLUE);
		return eb.build();
	}

	private MessageEmbed startGameMessageEmbededForm() {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription("참가자들을 랜덤으로 팀으로 나눕니다.");
		eb.setColor(Color.YELLOW);
		return eb.build();
	}
}
