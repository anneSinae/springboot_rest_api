package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	private int user_no;
	private String id;
	private String name;
	private String email;
	private String password;
	private String role;
	private String regi_tm;
	//private List<FileData> photoList;
	
	@Override
	public String toString() {
	    return this.id + " " + this.name + " " + this.password + " " + this.role;
	}
}
