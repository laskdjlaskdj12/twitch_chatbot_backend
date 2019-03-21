package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Controller.Handler;

import com.google.gson.Gson;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.Error.BusinessException;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
@Order(HandlerConstants.PRIORITY_LOWER)
public class CustomExceptionHandler {
	@Autowired
	ExceptionService exceptionService;

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<String> onThrowBaseException(BusinessException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Map<String, Object> map = new HashMap<>();
		String message = e.getMessage();
		map.put("Error", message);
		map.put("ErrorType", e.getErrorType());

		return new ResponseEntity<>(new Gson().toJson(map), status);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(HttpServletRequest request, HttpServletResponse response, HttpSession session, Throwable throwable) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		exceptionService.saveReportFile(request, throwable);

		return new ResponseEntity<>(status);
	}
}