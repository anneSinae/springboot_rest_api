package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public User getUsersInfo() {
		User user = new User();
		user.setName("유신애");
		user.setEmail("anne@test.com");
		return user;
	}

	public List<User> getUserList() {
		return this.boardRepository.findList();
	}
}
