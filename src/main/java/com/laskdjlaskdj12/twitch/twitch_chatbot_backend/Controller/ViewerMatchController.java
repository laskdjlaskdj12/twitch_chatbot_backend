package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Controller;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.MatchInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.StartLotteryDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ViewerMatchApplyDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ResultVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service.MatchInfoService;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service.ViewerMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/viewer/match")
public class ViewerMatchController {

	@Autowired
	private ViewerMatchService viewerMatchService;

	@Autowired
	private MatchInfoService matchInfoService;

	@PostMapping("/create")
	public ResultVO createMatch(@RequestBody @Valid MatchInfoDTO matchInfoDTO){
		return matchInfoService.createMatch(matchInfoDTO);
	}

	@PostMapping("/apply")
	public ResultVO viewerMatchApply(@RequestBody @Valid ViewerMatchApplyDTO applyFormListDTO){
		return viewerMatchService.apply(applyFormListDTO);
	}

	@PostMapping("/lottery")
	public List<String> lotteryViewerMatch(@RequestBody @Valid StartLotteryDTO startLotteryDTO){
		return viewerMatchService.lottery(startLotteryDTO);
	}

	@PostMapping("/applycount")
	public Integer applyCount(@RequestBody @Valid String creator){
		return viewerMatchService.getApplyCount(creator);
	}
}
