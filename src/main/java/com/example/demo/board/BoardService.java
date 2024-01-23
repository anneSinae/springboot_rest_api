package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Board;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public List<Board> findList() {
		return this.boardRepository.findList();
	}
}
