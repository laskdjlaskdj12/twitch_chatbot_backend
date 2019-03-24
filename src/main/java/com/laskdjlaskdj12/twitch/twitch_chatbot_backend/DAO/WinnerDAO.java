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
	public WinnerDAO(DataSource dataSource){
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("winner")
				.usingGeneratedKeyColumns("PK");
	}

	public int insertWinnerInfo(int matchInfoPK, int userInfoPK, int emailVOPK){
		Map<String,Object> parameter = new HashMap<>();

		parameter.put("matchInfoPK", matchInfoPK);
		parameter.put("userPK", userInfoPK);
		parameter.put("emailPK", emailVOPK);

		return simpleJdbcInsert.executeAndReturnKey(parameter).intValue();
	}
}
