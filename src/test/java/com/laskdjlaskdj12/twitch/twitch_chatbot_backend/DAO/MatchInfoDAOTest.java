package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchInfoDAOTest {

	@Autowired
	private MatchInfoDAO matchInfoDAO;

	@Test
	public void getMatchInfoByPKTest(){
		MatchInfoVO matchInfoVO = matchInfoDAO.getMatchByPK(24);

		boolean is_match_info_vo_is_not_null_true = matchInfoVO != null;

		Assert.assertTrue(is_match_info_vo_is_not_null_true);
	}

	@Test
	public void getMatchInfoByNameTest(){
		MatchInfoVO matchInfoVO = matchInfoDAO.getCreateRecentMatchInfo("laskdjlaskdj12");

		boolean is_match_info_vo_is_not_empty_true = matchInfoVO != null;

		Assert.assertTrue(is_match_info_vo_is_not_empty_true);
	}

	@Test
	public void insertTest(){
		int insertPK = matchInfoDAO.insert("laskdjlaskdj12", LocalDateTime.now(ZoneId.of("Asia/Seoul")), LocalDateTime.now().plusDays(10));
		Assert.assertTrue(insertPK > 0);
	}
}
