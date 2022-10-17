package com.example.demo.test;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TestRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public TestRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<User> findList(){
		String sql = "select * from users";
		
		log.debug("query : {}", sql);
		
		RowMapper<User> usersMapper = (rs, rowNum) -> {
			User user = new User();
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		};
		
		return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), usersMapper);
	}
	
	public List<User> getUserListByName(String name){
		String sql = "select * from users where name=:name";
		
		log.debug("query : {}", sql);
		MapSqlParameterSource param = new MapSqlParameterSource("name", name);
		
		RowMapper<User> usersMapper = (rs, rowNum) -> {
			User user = new User();
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		};
		
		return namedParameterJdbcTemplate.query(sql, param, usersMapper);
	}
	
	public User insert(User user) {
		String sql = "INSERT INTO users (name, email) values (:name, :email)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource("name", user.getName())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail()); 
		namedParameterJdbcTemplate.update(sql, param, keyHolder);
		//log.debug("{} inserted, new name = {}", affectedRows, keyHolder.getKey());
		//user.setId(keyHolder.getKey().intValue());
		return user;
	}
}
