package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Service;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.ApplyHistoryDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.EmailDAO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.MatchInfoDAO;
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
public class LotteryServiceTest {

	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private EmailDAO emailDAO;

	@Autowired
	private ApplyHistoryDAO applyHistoryDAO;

	@Autowired
	private MatchInfoDAO matchInfoDAO;

//  applyHistoryDAO, emailDAO, matchInfoDAO를 먼저 초기화를 한후에 사용할것
//	@Before
//	public void before(){
//		insertMatchVOMock();
//		insertApplyMock();
//	}

	@Test
	public void isValidApplyTest(){
		MatchInfoVO matchInfoVO = makeMatchInfoVO();
		List<ApplyVO> applyVOS = applyHistoryDAO.getApplyHistory(matchInfoVO);
		ApplyVO applyVO = applyVOS.get(0);

		boolean is_valid_true = lotteryService.isValidApply(applyVO);

		Assert.assertTrue(is_valid_true);
	}

	@Test
	public void lotteryByViewerMatchTest(){
		MatchInfoVO matchInfoVO = makeMatchInfoVO();

		List<ApplyVO> winnerList = lotteryService.lotteryByViewerMatch(matchInfoVO, 4);

		boolean is_winnerList_not_empty_true = !winnerList.isEmpty();
		boolean is_winner_size_is_4_true = winnerList.size() == 4;

		Assert.assertTrue(is_winnerList_not_empty_true);
		Assert.assertTrue(is_winner_size_is_4_true);

		for (ApplyVO applyVO : winnerList){
			System.out.println(applyVO);
		}
	}

	private int insertMatchVOMock() {
		return matchInfoDAO.insert("laskdjlaskdj12", LocalDateTime.now(), LocalDateTime.now().plusDays(10));
	}

	private void insertApplyMock() {
		MatchInfoVO matchInfoVO = matchInfoDAO.getByCreator("laskdjlaskdj12");

		for(int i = 1; i < 51; i++){
			UserInfoVO userInfoVO = new UserInfoVO();
			userInfoVO.setName("laskdjlaskdj12");
			userInfoVO.setPK(i);
			userInfoVO.setId(i);

			String email = "laskdjlaskdj" + i + "@gmail.com";
			int emailPK = emailDAO.insert(userInfoVO, email);

			applyHistoryDAO.addHistory(userInfoVO, emailPK, matchInfoVO, LocalDateTime.now());
		}
	}

	private MatchInfoVO makeMatchInfoVO() {
		return matchInfoDAO.getByCreator("laskdjlaskdj12");
	}
}
