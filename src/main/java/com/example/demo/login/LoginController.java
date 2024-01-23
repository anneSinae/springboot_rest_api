package com.example.demo.login;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;

@RestController
public class LoginController {
	
	private String basePath = "login/";
	private LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@RequestMapping("/login")
	public ModelAndView Hello() {
		ModelAndView view = new ModelAndView(this.basePath + "login");

		List<User> userList = loginService.getUserList();
		view.addObject("userList", userList);
		return view;
	}
	
}