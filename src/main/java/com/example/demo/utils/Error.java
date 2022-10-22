package com.example.demo.utils;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Error implements ErrorController {
	
	@RequestMapping("/error") // 스프링부트 기본설정 : server.error.path=/error
    public ModelAndView handleError(HttpServletResponse response, Model info) {
        ModelAndView view = new ModelAndView();
        
        info.addAttribute("code", response.getStatus());
        info.addAttribute("timestamp", new Date());
        view.addObject("error", info);
        
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
        	view.setViewName("/error/404");
        }
        else view.setViewName("/error/error");
 
        return view;
    }
	
	
}