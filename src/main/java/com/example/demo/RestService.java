package com.example.demo;

import org.springframework.stereotype.Service;
import com.example.demo.rest.users.model.Users;

@Service
public class RestService {
	public Users getUsersInfo() {
		Users users = new Users();
		users.name = "유신애";
		users.email = "sinae@test.com";
		
		return users;
	}
}
