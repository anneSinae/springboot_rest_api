package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserService {

	private final UserRepository loginRepository;

	@Autowired
	public UserService(UserRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public List<User> getUserList() {
		return this.loginRepository.findList();
	}
}
