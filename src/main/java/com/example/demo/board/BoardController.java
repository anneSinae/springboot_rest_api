package com.example.demo.board;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Board;

@RestController
public class BoardController {
	
	private String basePath = "board/";
	private BoardService boardController;
	
	public BoardController(BoardService boardController) {
		this.boardController = boardController;
	}
	
	@GetMapping("/board")
	public ModelAndView Hello() {
		ModelAndView view = new ModelAndView(this.basePath + "main");

		List<Board> boardList = boardController.findList();
		view.addObject("boardList", boardList);
		return view;
	}
	
}