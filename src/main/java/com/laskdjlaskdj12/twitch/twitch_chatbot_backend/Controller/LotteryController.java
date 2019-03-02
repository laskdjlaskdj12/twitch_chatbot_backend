package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Controller;

import com.google.gson.Gson;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

	@PostMapping("/apply")
	public ResponseEntity<String> applyLottery(UserInfoDTO userInfoDTO){
		Map<String,Object> result = new HashMap<>();
		result.put("Result", "Success");
		return new ResponseEntity<>(new Gson().toJson(result), HttpStatus.OK);
	}
}
