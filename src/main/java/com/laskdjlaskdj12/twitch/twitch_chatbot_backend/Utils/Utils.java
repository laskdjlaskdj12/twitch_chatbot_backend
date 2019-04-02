package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static LocalDateTime stringToLocalDateTime(String timeFormat) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(timeFormat, formatter);
	}

	public static String localDateTimeToString(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return formatter.format(localDateTime);
	}
}
