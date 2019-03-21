package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper.MatchInfoMapper;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Nullable
	public MatchInfoVO getMatchInfoByCreator(String creator) {
		String query = "SELECT * FROM matchInfo WHERE creator = ?";

		List<MatchInfoVO> matchInfoVOList = jdbcTemplate.query(query, new MatchInfoMapper(), creator);

		if(matchInfoVOList.isEmpty()) {
			return null;
		}

		return matchInfoVOList.get(0);
	}
}
