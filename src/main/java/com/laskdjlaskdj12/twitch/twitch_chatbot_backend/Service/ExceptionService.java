package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.google.gson.Gson;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExceptionService {

	final private String logFilePath;

	public ExceptionService(){
		Path currentRelativePath = Paths.get("");
		String currentFileDir = currentRelativePath.toAbsolutePath().toString();
		logFilePath = currentFileDir + "/log";
	}

	public void saveReportFile(HttpServletRequest request, Throwable throwable) {
		Map<String, String> headers = getHeaders(request);
		Map<String, String> cookies = getCookies(request);
		String method = request.getMethod();
		StringBuffer URL = request.getRequestURL();
		String URI = request.getRequestURI();
		String remoteHost = request.getRemoteHost();
		String queryString = request.getQueryString();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("URL : " + URL + "\n");
		stringBuilder.append("URI : " + URI + "\n");
		stringBuilder.append("remoteHost : " + remoteHost + "\n");
		stringBuilder.append("Cookie : " + new Gson().toJson(cookies) + "\n");
		stringBuilder.append("method : " + method + "\n");
		stringBuilder.append("queryString : " + queryString + "\n");
		stringBuilder.append("headers : " + new Gson().toJson(headers) + "\n");
		stringBuilder.append("error message : " + throwable.getMessage() + "\n");
		stringBuilder.append("stack Tracke : " + getExceptionStackTrace(throwable, 3));

		String reportFileName = makeReportFileName();

		saveFile(stringBuilder.toString(), reportFileName);
	}

	public String makeReportFileName(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());

		return logFilePath + "/" + time + ".txt";
	}

	public void saveFile(String errorString, String reportFileName) {
		File file = new File(String.format(reportFileName));
		try {
			final FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(errorString);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	Map<String, String> getCookies(HttpServletRequest r) {
		Map<String, String> map = new HashMap<>();
		Cookie[] cookies = r.getCookies();
		if (cookies != null) {
			Arrays.stream(cookies).forEach(cookie -> map.put(cookie.getName(), cookie.getValue()));
		}
		return map;
	}

	Map<String, String> getHeaders(HttpServletRequest r) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> headerNames = r.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			map.put(name, r.getHeader(name));
		}
		return map;
	}

	private String getExceptionStackTrace(Throwable e, int line) {
		String[] stackTrace = ExceptionUtils.getRootCauseStackTrace(e);
		StringBuilder st = new StringBuilder();
		for (int i = 0; i < stackTrace.length; i++) {
			if (i > line) {
				break;
			}
			st.append(String.format("%s\n", stackTrace[i]));
		}
		return st.toString();
	}
}
