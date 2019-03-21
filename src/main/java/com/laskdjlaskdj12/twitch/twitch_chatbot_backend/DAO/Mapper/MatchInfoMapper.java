package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchInfoMapper implements RowMapper<MatchInfoVO> {
	@Override
	public MatchInfoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchInfoVO matchInfoVO = new MatchInfoVO();
		matchInfoVO.setPK(rs.getInt("PK"));
		matchInfoVO.setCreator(rs.getString("creator"));
		matchInfoVO.setStartTime(rs.getTimestamp("startTime").toLocalDateTime());
		matchInfoVO.setEndTime(rs.getTimestamp("endTime").toLocalDateTime());
		return matchInfoVO;
	}
}
