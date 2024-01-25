package com.example.demo.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileData {
	private String filename;
    private String url;
    private int file_no;
    private int user_no;
    private Long size;
    //private Integer insertId;
	//private Date insertDt;
	//private String category;
}