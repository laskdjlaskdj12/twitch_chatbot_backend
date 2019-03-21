package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ViewerApplyMatchHistoryDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public ViewerApplyMatchHistoryDAO(DataSource dataSource){
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("viewerMatch")
				.usingGeneratedKeyColumns("PK");
	}

	public Integer addHistory(UserInfoVO userInfoVO, Integer emailVOPK, MatchInfoVO matchInfoVO, LocalDateTime currentDateTime) {
		Map<String, Object> paramter = new HashMap<>();
		paramter.put("userInfoPK", userInfoVO.getPK());
		paramter.put("emailPK", emailVOPK);
		paramter.put("matchInfoPK", matchInfoVO.getPK());
		paramter.put("currentTime", currentDateTime);
		return simpleJdbcInsert.executeAndReturnKey(paramter).intValue();
	}
}
