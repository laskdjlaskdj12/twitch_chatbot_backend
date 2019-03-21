package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.UserInfoVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserInfoMapper implements RowMapper<UserInfoVO> {

	@Override
	public UserInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setName(rs.getString("name"));
		userInfoVO.setId(rs.getLong("id"));
		userInfoVO.setPK(rs.getInt("PK"));
		return userInfoVO;
	}
}
