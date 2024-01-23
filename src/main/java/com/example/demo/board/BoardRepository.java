package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardRepository {
	
	private final NamedParameterJdbcTemplate jdbcTmpl;
	
	public BoardRepository(@Qualifier("namedJdbcTmpl1") NamedParameterJdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	public List<Board> findList(){
		String sql = "select * from board";
		
		log.debug("query : {}", sql);
		
		RowMapper<Board> boardMapper = (rs, rowNum) -> {
			Board board = new Board();
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			return board;
		};
		
		return jdbcTmpl.query(sql, new MapSqlParameterSource(), boardMapper);
	}
}
