package com.example.demo.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Board {
	private int id;
	private String title;
	private String content;
	private String read_cnt;
	private String register_id;
	private String register_time;
	private String update_time;
}
