package com.example.demo.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	
	private String basePath = "/home/";
	
	@RequestMapping("/")
	public ModelAndView Home() {
		return new ModelAndView(this.basePath + "home");
	}
	
}