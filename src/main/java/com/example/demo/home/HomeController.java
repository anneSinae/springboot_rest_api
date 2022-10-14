package com.example.demo.home;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("home")
public class HomeController {
	
	private String basePath = "/home/";
	
	@GetMapping("")
	public ModelAndView Home() {
		return new ModelAndView(this.basePath + "home");
	}
	
}