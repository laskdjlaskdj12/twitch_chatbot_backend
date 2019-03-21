package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper.EmailMapper;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmailDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public EmailDAO(DataSource dataSource){
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("email")
				.usingGeneratedKeyColumns("PK");
	}

	@Nullable
	public EmailVO getEmailByUserPK(int userPK) {
		String sql = "SELECT * FROM email WHERE userPK = ?";

		List<EmailVO> emailVOList = jdbcTemplate.query(sql, new EmailMapper(), userPK);

		if(emailVOList.isEmpty()){
			return null;
		}

		return emailVOList.get(0);
	}

	public List<EmailVO> getUserByEmail(String email) {
		String sql = "SELECT * FROM email WHERE email = ?";

		return jdbcTemplate.query(sql, new EmailMapper(), email);
	}

	public Integer insert(UserInfoVO userInfoVO, String email) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		parameters.put("userPK", userInfoVO.getPK());

		Number PK = simpleJdbcInsert.executeAndReturnKey(parameters);
		return PK.intValue();
	}
}
