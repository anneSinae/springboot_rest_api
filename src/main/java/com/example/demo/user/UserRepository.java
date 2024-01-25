package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserRepository {
	private final NamedParameterJdbcTemplate jdbcTmpl;
	
	public UserRepository(
			@Qualifier("namedJdbcTmpl1") NamedParameterJdbcTemplate jdbcTmpl
		) {
		this.jdbcTmpl = jdbcTmpl;
	}

	RowMapper<User> usersMapper = (rs, rowNum) -> {
		User user = new User();
		user.setUser_no(rs.getInt("user_no"));
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		return user;
	};

	public List<User> findList(){
		String sql = "select * from users";
		return jdbcTmpl.query(sql, new MapSqlParameterSource(), this.usersMapper);
	}
}
