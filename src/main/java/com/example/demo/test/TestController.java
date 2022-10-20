package com.example.demo.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;
import com.example.demo.model.FileData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("users")
public class TestController {
	
	private TestService testService;
	
	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@GetMapping({"", "home"})
	public ModelAndView testInfo() {
		ModelAndView view = new ModelAndView("test/test");
		view.addObject("userList", testService.getUserList());
		return view;
	}
	
	@GetMapping("detail/{name}") //http://localhost:8080/info/users/원펀맨
	public List<User> testInfoByPathVariable(@PathVariable("name") String name) {
		List<User> userList = testService.getUserListByName(name);
		return userList;
	}

	@GetMapping("detail") //http://localhost:8080/info/users?name=원펀맨
	public List<User> testInfoRequestParam(@RequestParam(value = "name", required = false, defaultValue = "홍길동") String name) {
		List<User> userList = testService.getUserListByName(name);
		return userList;
	}
	
	@GetMapping(value="list")
	public ModelAndView reloadList() {
		ModelAndView view = new ModelAndView("test/test :: users_wrap");
		view.addObject("userList", testService.getUserList());
	    return view;
	}
	
	@PutMapping(value="user") //생성요청마다 ok하려면 Post, 동일데이터 생성요청시 최초요청만 ok하려면 Put
	public ResponseEntity<User> testAdd(@RequestBody User user) {
		return new ResponseEntity<>(testService.insert(user), HttpStatus.OK);
	}/*ResponseEntity<> : Http상태, 응답헤더, 응답데이터 등을 포함 */
	
	@PutMapping(value="manage/{id}")
	public ResponseEntity<Integer> testUpdate(@RequestBody User user) {
		return new ResponseEntity<>(testService.update(user), HttpStatus.OK);
	}
	
	@DeleteMapping(value="manage/user")
	public ResponseEntity<Integer> testDelete(@RequestParam(value = "id") int id) {
		return new ResponseEntity<>(testService.delete(id), HttpStatus.OK);
	}
	
	@PostMapping(value="manage/user2")
	public ResponseEntity<Object> testDelete2(@RequestParam Map<String, Object> param) throws URISyntaxException {
		Integer id = Integer.valueOf(param.get("delId").toString());
		testService.delete(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("http://localhost:8080/users"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}
	
	@GetMapping("photo/{id}")
	public List<FileData> getUserPhoto(@PathVariable("id") int id) {
		System.out.println(id + " ////////////");
		List<FileData> fileList = testService.getUserPhoto(id);
		return fileList;
	}
}