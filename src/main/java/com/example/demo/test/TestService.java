package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class TestService {

	private final TestRepository testRepository;

	@Autowired
	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	public User getUsersInfo() {
		User user = new User();
		user.setName("유신애");
		user.setEmail("anne@test.com");
		return user;
	}

	public List<User> getUserList() {
		return this.testRepository.findList();
	}
	
	public List<User> getUserListByName(String name) {
		return this.testRepository.getUserListByName(name);
	}
}
