package com.example.demo.board;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;
import com.example.demo.test.TestService;

@RestController
@RequestMapping("board")
public class BoardController {
	
	private String basePath = "/board/";
	private TestService testService;
	
	public BoardController(TestService testService) {
		this.testService = testService;
	}
	
	@GetMapping("")
	public ModelAndView Hello() {
		ModelAndView view = new ModelAndView(this.basePath + "main");
		
		List<User> userList = testService.getUserList();
		view.addObject("userList", userList);
		return view;
	}
	
}