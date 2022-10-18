package com.example.demo.test;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
		RowMapper<User> usersMapper = (rs, rowNum) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		};
		return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), usersMapper);
	}
	
	public List<User> getUserListByName(String name){
		String sql = "select * from users where name=:name";
		MapSqlParameterSource param = new MapSqlParameterSource("name", name);
		RowMapper<User> usersMapper = (rs, rowNum) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		};
		return namedParameterJdbcTemplate.query(sql, param, usersMapper);
	}
	
	public User insert(User user) {
		String sql = "INSERT INTO users (name, email) values (:name, :email)";
		SqlParameterSource param = new MapSqlParameterSource("name", user.getName())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail()); 
		namedParameterJdbcTemplate.update(sql, param);
		return user;
	}
	
	public int update(User user) {
		String sql = "UPDATE users SET name = :name, email = :email WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource("id", user.getId())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail());
		return namedParameterJdbcTemplate.update(sql, param);
	}
}
