package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ViewerMatchApplyDTO {

	@NotNull
	private String managerName;

	@Nullable
	private List<ApplyFormDTO> applyViewerFormList;
}
