package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rest.users.model.Users;

@RestController
public class TestController {
	
	private RestService restService;
	
	@Autowired
	public TestController(RestService restService) {
		this.restService = restService;
	}
	
	@GetMapping("/info")
	public Object restInfo() {
		Users users = restService.getUsersInfo();
		return users;
	}
}
