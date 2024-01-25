package com.example.demo.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class User {
	private int user_no;
	private String id;
	private String name;
	private String email;
	private String password;
	//private List<FileData> photoList;
}
