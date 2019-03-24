package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper.MatchInfoMapper;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatchInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public MatchInfoDAO(DataSource dataSource){
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("matchInfo")
				.usingGeneratedKeyColumns("PK");
	}

	@Nullable
	public MatchInfoVO getByCreator(String creator) {
		String query = "SELECT * FROM matchInfo WHERE creator = ?";

		List<MatchInfoVO> matchInfoVOList = jdbcTemplate.query(query, new MatchInfoMapper(), creator);

		if(matchInfoVOList.isEmpty()) {
			return null;
		}

		return matchInfoVOList.get(0);
	}

	public int insert(String creator, LocalDateTime startTime, LocalDateTime endTime){
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("creator", creator);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);

		return simpleJdbcInsert.executeAndReturnKey(parameter).intValue();
	}
}
