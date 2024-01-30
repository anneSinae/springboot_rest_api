package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

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
		user.setPassword(rs.getString("password"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setRegi_tm(rs.getString("regi_tm"));
		user.setRole(rs.getString("role"));
		return user;
	};

	public List<User> findList(){
		String sql = "select * from users";
		return jdbcTmpl.query(sql, new MapSqlParameterSource(), this.usersMapper);
	}
	
	public User getUserById(String id){
		String sql = "select * from users where id=:id";
		MapSqlParameterSource param = new MapSqlParameterSource("id", id);
		return jdbcTmpl.query(sql, param, this.usersMapper).get(0);
	}
	
	public int createUser(User user) {
		String sql = "INSERT INTO users (id, name, email, password) values (:id, :name, :email, :password)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", user.getId())
				.addValue("name", user.getName())
				.addValue("password", user.getPassword())
				.addValue("email", user.getEmail()); 
		return jdbcTmpl.update(sql, param);
	}
	
	public int chkDuplicatedId(String id) {
		String sql = "select count(*) from users where id = :id";
		return jdbcTmpl.queryForObject(sql, new MapSqlParameterSource("id", id), Integer.class);
	}
	
	
}
