package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper.UserInfoMapper;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.UserInfoDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public UserInfoDAO(DataSource dataSource){
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
		.withTableName("userInfo")
		.usingGeneratedKeyColumns("PK");
	}

	public UserInfoVO getUserByID(long id) {
		String sql = "SELECT * FROM userInfo WHERE id = ?";
		List<UserInfoVO> userInfoVOList = jdbcTemplate.query(sql, new UserInfoMapper(), id);
		if(userInfoVOList.isEmpty()){
			return null;
		}

		return userInfoVOList.get(0);
	}

	public int addUser(UserInfoDTO userInfoDTO) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", userInfoDTO.getId());
		parameters.put("name", userInfoDTO.getName());
		Number PK = simpleJdbcInsert.executeAndReturnKey(parameters);
		return PK.intValue();
	}
}
