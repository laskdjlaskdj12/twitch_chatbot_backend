package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ViewerApplyMapper implements RowMapper<ApplyTwitchUserVO> {

	@Override
	public ApplyTwitchUserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApplyTwitchUserVO applyTwitchUserVO = new ApplyTwitchUserVO();
		applyTwitchUserVO.setPK(rs.getInt("PK"));
		applyTwitchUserVO.setId(rs.getLong("id"));
		applyTwitchUserVO.setName(rs.getString("name"));
		applyTwitchUserVO.setEmail(rs.getString("email"));
		applyTwitchUserVO.setMatchInfoPK(rs.getInt("matchInfoPK"));

		return applyTwitchUserVO;
	}

}
