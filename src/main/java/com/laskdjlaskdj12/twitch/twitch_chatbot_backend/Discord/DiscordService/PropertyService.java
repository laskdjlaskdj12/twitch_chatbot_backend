package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Discord.DiscordService;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PropertyService {

	private Map<String, Object> propertyList = new HashMap<>();

	public PropertyService(){
		String jsonContent = null;

		try {
			InputStream inputStream = new ClassPathResource("static/Discord.config").getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			jsonContent = new String(bdata, StandardCharsets.UTF_8);
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

