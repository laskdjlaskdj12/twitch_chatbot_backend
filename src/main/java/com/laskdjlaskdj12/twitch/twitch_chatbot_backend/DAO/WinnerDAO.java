package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class WinnerDAO {

	SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public WinnerDAO(DataSource dataSource) {
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("winnerViewer")
				.usingGeneratedKeyColumns("PK");
	}

	public int insertWinner(int matchInfoPK, Integer viewerApplyPK) {
		Map<String, Object> parameter = new HashMap<>();

		parameter.put("matchInfoPK", matchInfoPK);
		parameter.put("viewerApplyPK", viewerApplyPK);

		return simpleJdbcInsert.executeAndReturnKey(parameter).intValue();
	}
}
