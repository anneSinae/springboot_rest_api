package com.example.demo.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class LoginService {

	private final LoginRepository loginRepository;

	@Autowired
	public LoginService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public List<User> getUserList() {
		return this.loginRepository.findList();
	}
}
