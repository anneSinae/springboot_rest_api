package com.example.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Equip;
import com.example.demo.model.FileData;
import com.example.demo.model.User;

@Service
public class TestService {

	private final TestRepository testRepository;

	@Autowired
	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	public List<User> getUserList() {
		return this.testRepository.findList();
	}
	
	public List<User> getUserListByName(String name) {
		return this.testRepository.getUserListByName(name);
	}
	
	public User insert(User user) {
		return this.testRepository.insert(user);
	}
	
	public int update(User user) {
		return testRepository.update(user);
	}
	
	public int delete(int id) {
		return testRepository.delete(id);
	}
	
	public List<FileData> getUserPhoto(int id) {
		return testRepository.getUserPhoto(id);
	}
	
	public void insertPhoto(int user_id, String path) {
		testRepository.insertPhoto(user_id, path);
	}
	
	public User tempGetLastUser() {
		return this.testRepository.tempGetLastUser();
	}
	
	public List<Equip> getEquipList(){
		return this.testRepository.getEquipList();
	}
}
