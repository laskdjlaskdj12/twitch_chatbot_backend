package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplyMapper implements RowMapper<ApplyVO> {

	@Override
	public ApplyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApplyVO applyVO = new ApplyVO();
		applyVO.setPK(rs.getInt("PK"));
		applyVO.setUserPK(rs.getInt("userPK"));
		applyVO.setEmailPK(rs.getInt("emailPK"));
		applyVO.setMatchInfoPK(rs.getInt("matchInfoPK"));
		applyVO.setCurrentTime(rs.getTimestamp("currentTime").toLocalDateTime());

		return applyVO;
	}
}
