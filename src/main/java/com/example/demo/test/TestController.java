package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("info")
public class TestController {
	
	private TestService testService;
	
	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@GetMapping("")
	public Object testInfo() {
		//User users = testService.getUsersInfo();
		List<User> userList = testService.getUserList();
		return userList;
	}
	
	@GetMapping("users/{name}") //http://localhost:8080/info/users/원펀맨
	public Object testInfoByPathVariable(@PathVariable("name") String name) {
		List<User> userList = testService.getUserListByName(name);
		return userList;
	}

	@GetMapping("users") //http://localhost:8080/info/users?name=원펀맨
	public Object testInfoRequestParam(@RequestParam(value = "name", required = false, defaultValue = "홍길동") String name) {
		List<User> userList = testService.getUserListByName(name);
		return userList;
	}
	
	@PostMapping(value="users/user")
	public ResponseEntity<User> testAdd(@RequestBody User user) {
		try {
			log.trace("user = {}", user.toString());
			return new ResponseEntity<>(testService.insert(user), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("users/form")
	public ModelAndView viewForm() {
		ModelAndView view = new ModelAndView("test/test");
		return view;
	}
}