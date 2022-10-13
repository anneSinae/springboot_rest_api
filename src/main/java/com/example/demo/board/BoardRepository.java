package com.example.demo.board;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public BoardRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<User> findList(){
		String sql = "select * from users limit 10";
		
		log.debug("query : {}", sql);
		
		RowMapper<User> usersMapper = (rs, rowNum) -> {
			User user = new User();
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		};
		
		return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), usersMapper);
	}
}
