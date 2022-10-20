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
    private Long size;
    //private Integer insertId;
	//private Date insertDt;
	//private String category;
}