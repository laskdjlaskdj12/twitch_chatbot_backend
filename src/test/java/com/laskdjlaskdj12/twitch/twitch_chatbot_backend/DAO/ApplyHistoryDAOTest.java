package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyHistoryDAOTest {

	@Autowired
	ApplyHistoryDAO viewerApplyMatchHistoryDAO;

	@Test
	public void addHistoryTest(){
		UserInfoVO userInfoVO = makeUserInfoVOMock();
		Integer emailVOPK = makeEmailVOPKMock();
		MatchInfoVO matchInfoVO = makeMatchInfoVOPK();
		LocalDateTime localDateTime = LocalDateTime.now();
		int resultPK = viewerApplyMatchHistoryDAO.addHistory(userInfoVO, emailVOPK, matchInfoVO, localDateTime);

		Assert.assertTrue(resultPK > 0);
	}

	@Test
	public void getApplyHistoryTest(){
		MatchInfoVO matchInfoVO = makeMatchInfoVOPK();
		List<ApplyVO> applyVOList = viewerApplyMatchHistoryDAO.getApplyHistory(matchInfoVO);

		boolean is_apply_not_empty_true = applyVOList.size() > 0;

		Assert.assertTrue(is_apply_not_empty_true);

		System.out.println("applyVoList : " + applyVOList.size());
	}

	private MatchInfoVO makeMatchInfoVOPK() {
		MatchInfoVO matchInfoVO = new MatchInfoVO();
		matchInfoVO.setPK(10);
		return matchInfoVO;
	}

	private Integer makeEmailVOPKMock() {
		return 1;
	}

	private UserInfoVO makeUserInfoVOMock() {
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setPK(1);
		return userInfoVO;
	}
}
