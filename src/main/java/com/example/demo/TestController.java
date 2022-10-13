package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;

@RestController
public class TestController {
	
	private TestService testService;
	
	@Autowired
	public TestController(TestService restService) {
		this.testService = restService;
	}
	
	@GetMapping("/info")
	public Object restInfo() {
		//User users = testService.getUsersInfo();
		List<User> userList = testService.getUserList();
		return userList;
	}
}
