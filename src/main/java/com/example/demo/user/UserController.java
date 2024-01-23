package com.example.demo.user;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;

@RestController
@RequestMapping("user")
public class UserController {
	
	private String basePath = "user/";
	private UserService userService;
	
	public UserController(UserService loginService) {
		this.userService = loginService;
	}
	
	@GetMapping("login")
	public ModelAndView Login() {
		return new ModelAndView(this.basePath + "login").addObject("userList", userService.getUserList());
	}
	
	@GetMapping("signup")
	public ModelAndView Signup() {
		return new ModelAndView(this.basePath + "signup");
	}
	
}