package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.model.FileData;
import com.example.demo.model.User;

@Repository
public class TestRepository {
	
	private final NamedParameterJdbcTemplate jdbcTmpl;
	private final NamedParameterJdbcTemplate jdbcTmpl2;
	
	public TestRepository(
			@Qualifier("namedJdbcTmpl1") NamedParameterJdbcTemplate jdbcTmpl,
			@Qualifier("namedJdbcTmpl2") NamedParameterJdbcTemplate jdbcTmpl2
		) {
		this.jdbcTmpl = jdbcTmpl;
		this.jdbcTmpl2 = jdbcTmpl2;
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
	
	public List<User> getUserListByName(String name){
		String sql = "select * from users where name=:name";
		MapSqlParameterSource param = new MapSqlParameterSource("name", name);
		return jdbcTmpl.query(sql, param, this.usersMapper);
	}
	
	public User insert(User user) {
		String sql = "INSERT INTO users (id, name, email, password) values (:id, :name, :email, :password)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", user.getId())
				.addValue("name", user.getName())
				.addValue("password", user.getPassword())
				.addValue("email", user.getEmail()); 
		jdbcTmpl.update(sql, param);
		return user;
	}
	
	public int update(User user) {
		String sql = "UPDATE users SET id = :id, name = :name, email = :email WHERE user_no = :user_no"; //where조건은 사용자 변경없는 컬럼 사용 
		SqlParameterSource param = new MapSqlParameterSource("user_no", user.getUser_no())
				.addValue("id", user.getId())
				.addValue("name", user.getName())
				.addValue("email", user.getEmail());
		return jdbcTmpl.update(sql, param);
	}
	
	public int delete(int user_no) {
		String sql = "DELETE from users WHERE user_no = :user_no";
		SqlParameterSource param = new MapSqlParameterSource("user_no", user_no);
		return jdbcTmpl.update(sql, param);
	}
	
	public List<FileData> getUserPhoto(int user_no){
		String sql = "select * from files where user_no = :user_no";
		MapSqlParameterSource param = new MapSqlParameterSource("user_no", user_no);
		RowMapper<FileData> fileMapper = (rs, rowNum) -> {
			FileData file = new FileData();
			file.setFilename(rs.getString("path"));
			file.setUser_no(rs.getInt("user_no"));
			return file;
		};
		return jdbcTmpl.query(sql, param, fileMapper);
	}
	
	public void insertPhoto(int user_no, String path) {
		System.out.println(user_no + " rep////" + path);
		String sql = "INSERT INTO files (user_no, path) values (:user_no, :path)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("user_no", user_no)
				.addValue("path", path); 
		jdbcTmpl.update(sql, param);
	}
	
	public User tempGetLastUser() {
		String sql = "select * from users ORDER BY id DESC LIMIT 1";
		return jdbcTmpl.query(sql, new MapSqlParameterSource(), this.usersMapper).get(0);
	}
}
