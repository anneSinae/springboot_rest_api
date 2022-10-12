package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rest.users.model.Users;

@RestController
public class restController {
	@GetMapping("/info")
	public Object restInfo() {
		Users users = new Users();
		users.name = "유신애";
		users.email = "sinae@test.com";
		return users;
	}
}
