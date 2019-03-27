package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WinnerDAOTest {

	@Autowired
	WinnerDAO winnerDAO;

	@Test
	public void insertTest(){
		int result = winnerDAO.insertWinner(1, 10);
		boolean is_insert_row_over_0_true = result > 0;
		Assert.assertTrue(is_insert_row_over_0_true);
	}
}
