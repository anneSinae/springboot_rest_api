package com.example.demo.board;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("board")
public class BoardController {
	
	private String basePath = "/board/";
	
	@GetMapping("")
	public ModelAndView Hello() {
		return new ModelAndView(this.basePath + "main");
	}
	
}