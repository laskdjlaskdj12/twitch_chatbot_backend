package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.EmailVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
public class EmailMapper implements RowMapper<EmailVO> {
	@Override
	public EmailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmailVO emailVO = new EmailVO();

		emailVO.setPK(rs.getInt("PK"));
		emailVO.setEmail(rs.getString("email"));
		emailVO.setUserPK(rs.getInt("userPK"));

		return emailVO;
	}
}
