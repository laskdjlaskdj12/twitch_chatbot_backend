package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Controller;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.StartLotteryDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ViewerMatchApplyDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ResultVO;
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

	@PostMapping("/apply")
	public ResultVO viewerMatchApply(@RequestBody @Valid ViewerMatchApplyDTO applyFormListDTO){
		return viewerMatchService.apply(applyFormListDTO);
	}

	@PostMapping("/lottery")
	public List<EmailVO> lotteryViewerMatch(@RequestBody @Valid StartLotteryDTO startLotteryDTO){
		return viewerMatchService.lottery(startLotteryDTO);
	}
}
