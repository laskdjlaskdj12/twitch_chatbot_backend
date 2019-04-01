package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertyService {

	private Map<String, Object> propertyList = new HashMap<>();

	public PropertyService(){
		String jsonContent = null;

		try {
			File propertyFile =  new ClassPathResource("Discord.config").getFile();
			jsonContent = FileUtils.readFileToString(propertyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		propertyList = gson.fromJson(jsonContent, HashMap.class);
	}

	public String getPorperty(String key){
		return propertyList.get(key).toString();
	}
}

