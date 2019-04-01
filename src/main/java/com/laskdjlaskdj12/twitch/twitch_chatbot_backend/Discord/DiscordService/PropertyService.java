package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService;

import ch.qos.logback.core.util.FileUtil;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PropertyService {

	private Map<String, Object> propertyList = new HashMap<>();

	public PropertyService(){
		String jsonContent = null;

		try {
//			ResourceUtils.getFile("Discord.config");
			File propertyFile = new ClassPathResource("Discord.config").getFile();
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

