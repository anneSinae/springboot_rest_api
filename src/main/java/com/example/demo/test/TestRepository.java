package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import com.example.demo.model.Equip;
import com.example.demo.model.FileData;


@Repository
public class TestRepository {
	
	private final NamedParameterJdbcTemplate jdpcTmpl;
	private final NamedParameterJdbcTemplate jdpcTmpl2;
	
	public TestRepository(
			@Qualifier("namedJdbcTmpl1") NamedParameterJdbcTemplate jdpcTmpl,
			@Qualifier("namedJdbcTmpl2") NamedParameterJdbcTemplate jdpcTmpl2
		) {
		this.jdpcTmpl = jdpcTmpl;
		this.jdpcTmpl2 = jdpcTmpl2;
	}
	
	RowMapper<User> usersMapper = (rs, rowNum) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		return user;
	};

	public List<User> findList(){
		String sql = "select * from users";
		return jdpcTmpl.query(sql, new MapSqlParameterSource(), this.usersMapper);
	}
	
	public List<User> getUserListByName(String name){
		String sql = "select * from users where name=:name";
		MapSqlParameterSource param = new MapSqlParameterSource("name", name);
		return jdpcTmpl.query(sql, param, this.usersMapper);
	}
	
	public User insert(User user) {
		String sql = "INSERT INTO users (name, email) values (:name, :email)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", user.getName())
				.addValue("email", user.getEmail()); 
		jdpcTmpl.update(sql, param);
		System.out.println(user.getId() + " rep insert///");
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
		String sql = "select * from files where user_id = :id";
		MapSqlParameterSource param = new MapSqlParameterSource("id", id);
		RowMapper<FileData> fileMapper = (rs, rowNum) -> {
			FileData file = new FileData();
			file.setFilename(rs.getString("path"));
			file.setUserId(rs.getInt("user_id"));
			return file;
		};
		return jdpcTmpl.query(sql, param, fileMapper);
	}
	
	public void insertPhoto(int user_id, String path) {
		System.out.println(user_id + " rep////" + path);
		String sql = "INSERT INTO files (user_id, path) values (:user_id, :path)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("user_id", user_id)
				.addValue("path", path); 
		jdpcTmpl.update(sql, param);
	}
	
	public User tempGetLastUser() {
		String sql = "select * from users ORDER BY id DESC LIMIT 1";
		return jdpcTmpl.query(sql, new MapSqlParameterSource(), this.usersMapper).get(0);
	}
	
	public List<Equip> getEquipList(){
		String sql = "select eq_id, eq_nm from dbo.bEquip";
		RowMapper<Equip> listMapper = (rs, rowNum) -> {
			Equip equip = new Equip();
			equip.setEq_id(rs.getInt("eq_id"));
			equip.setEq_nm(rs.getString("eq_nm"));
			return equip;
		};
		return jdpcTmpl2.query(sql, new MapSqlParameterSource(), listMapper); //send to jdpcTmpl2
	}
}
