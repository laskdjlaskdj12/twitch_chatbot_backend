package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

	public List<ApplyTwitchUserVO> filterDuplicatedEmail(List<ApplyTwitchUserVO> applyUserList) {
		List<ApplyTwitchUserVO> twitchUserVOS = new ArrayList<>(applyUserList);
		List<ApplyTwitchUserVO> duplicatedList = new ArrayList<>();

		for (ApplyTwitchUserVO applyTwitchUserVO : twitchUserVOS) {
			if (isAlreadyDuplicated(duplicatedList, applyTwitchUserVO)) {
				continue;
			}

			List<ApplyTwitchUserVO> duplicatedUserList = getDuplicateEmail(twitchUserVOS, applyTwitchUserVO.getEmail());
			if (duplicatedUserList.size() == 1) {
				continue;
			}

			//첫번째를 제외하고 전부 중복된 리스트에 넣음
			duplicatedUserList.remove(0);

			if (!duplicatedList.addAll(duplicatedUserList)) {
				throw new RuntimeException("Add Duplicated List Fail By Email");
			}
		}

		twitchUserVOS.removeAll(duplicatedList);

		return twitchUserVOS;
	}

	private boolean isAlreadyDuplicated(List<ApplyTwitchUserVO> duplicatedList, ApplyTwitchUserVO applyTwitchUserVO) {
		return duplicatedList.stream()
				.anyMatch(applyTwitchUserVO1 -> applyTwitchUserVO1.getPK().equals(applyTwitchUserVO.getPK()));
	}

	private List<ApplyTwitchUserVO> getDuplicateEmail(List<ApplyTwitchUserVO> applyTwitchUserVOS, String email) {
		return applyTwitchUserVOS.stream()
				.filter(applyTwitchUserVO -> applyTwitchUserVO.getEmail().equals(email))
				.collect(Collectors.toList());
	}

	public List<ApplyTwitchUserVO> filterDuplicatedPlayer(List<ApplyTwitchUserVO> applyUserList) {
		List<ApplyTwitchUserVO> twitchUserVOS = new ArrayList<>(applyUserList);
		List<ApplyTwitchUserVO> duplicatedList = new ArrayList<>();

		for (ApplyTwitchUserVO applyTwitchUserVO : twitchUserVOS) {
			if (isAlreadyDuplicated(duplicatedList, applyTwitchUserVO)) {
				continue;
			}

			List<ApplyTwitchUserVO> duplicatedUserList = getDuplicatedUser(twitchUserVOS, applyTwitchUserVO.getId());
			if (duplicatedUserList.size() == 1) {
				continue;
			}

			//첫번째를 제외하고 전부 중복된 리스트에 넣음
			duplicatedUserList.remove(0);

			if (!duplicatedList.addAll(duplicatedUserList)) {
				throw new RuntimeException("Add Duplicated List Fail By Player");
			}
		}

		twitchUserVOS.removeAll(duplicatedList);

		return twitchUserVOS;
	}

	private List<ApplyTwitchUserVO> getDuplicatedUser(List<ApplyTwitchUserVO> twitchUserVOS, Long userID) {
		return twitchUserVOS.stream()
				.filter(applyTwitchUserVO -> applyTwitchUserVO.getId().equals(userID))
				.collect(Collectors.toList());
	}
}
