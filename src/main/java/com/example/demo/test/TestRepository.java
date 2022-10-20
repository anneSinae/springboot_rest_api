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
import com.example.demo.model.FileData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TestRepository {
	private final NamedParameterJdbcTemplate jdpcTmpl;
	
	public TestRepository(NamedParameterJdbcTemplate jdpcTmpl) {
		this.jdpcTmpl = jdpcTmpl;
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
		return jdpcTmpl.query(sql, new MapSqlParameterSource(), usersMapper);
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
		return jdpcTmpl.query(sql, param, usersMapper);
	}
	
	public User insert(User user) {
		String sql = "INSERT INTO users (name, email) values (:name, :email)";
		SqlParameterSource param = new MapSqlParameterSource("name", user.getName())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail()); 
		jdpcTmpl.update(sql, param);
		return user;
	}
	
	public int update(User user) {
		String sql = "UPDATE users SET name = :name, email = :email WHERE id = :id"; //where조건은 사용자 변경없는 컬럼 사용 
		SqlParameterSource param = new MapSqlParameterSource("id", user.getId())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail());
		return jdpcTmpl.update(sql, param);
	}
	
	public int delete(int id) {
		String sql = "DELETE from users WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		return jdpcTmpl.update(sql, param);
	}
	
	public List<FileData> getUserPhoto(int id){
		String sql = "select * from files where id in (select id from users where user_id = :id)";
		MapSqlParameterSource param = new MapSqlParameterSource("id", id);
		RowMapper<FileData> fileMapper = (rs, rowNum) -> {
			FileData file = new FileData();
			file.setFilename(rs.getString("path"));
			file.setUserId(rs.getInt("user_id"));
			return file;
		};
		return jdpcTmpl.query(sql, param, fileMapper);
	}
	
}
