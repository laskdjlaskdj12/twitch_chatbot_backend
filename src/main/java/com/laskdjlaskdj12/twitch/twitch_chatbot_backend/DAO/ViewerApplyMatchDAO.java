package com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO;

import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.DAO.Mapper.ViewerApplyMapper;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.DTO.ApplyFormDTO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.ApplyTwitchUserVO;
import com.laskdjlaskdj12.twitch.twitch_chatbot_backend.Domain.VO.MatchInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ViewerApplyMatchDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public ViewerApplyMatchDAO(DataSource dataSource){
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("viewerApply")
				.usingGeneratedKeyColumns("PK");
	}

	public int insert(ApplyFormDTO applyFormDTO, MatchInfoVO matchInfoVO){
		Map<String, Object> map = new HashMap<>();
		map.put("matchInfoPK", matchInfoVO.getPK());
		map.put("email", applyFormDTO.getEmail());
		map.put("id", applyFormDTO.getUserInfoDTO().getId());
		map.put("name", applyFormDTO.getUserInfoDTO().getName());

		return simpleJdbcInsert.executeAndReturnKey(map).intValue();
	}

	public List<ApplyTwitchUserVO> getApplyList(MatchInfoVO matchInfoVO){
		String query = "SELECT * FROM viewerApply where matchInfoPK = ?";
		return jdbcTemplate.query(query, new ViewerApplyMapper(), matchInfoVO.getPK());
	}

	public List<ApplyTwitchUserVO> getApplyByEmail(String email){
		String query = "SELECT * FROM viewerApply where email = ?";
		return jdbcTemplate.query(query, new ViewerApplyMapper(), email);
	}

	public List<ApplyTwitchUserVO> getApplyByViewerID(Long id) {
		String query = "SELECT * FROM viewerApply where id = ?";
		return jdbcTemplate.query(query, new ViewerApplyMapper(), id);
	}
}
