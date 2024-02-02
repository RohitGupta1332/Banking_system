package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

@Service
public class MainService {

	@Autowired
	UserDao userDao;
	public boolean isExists(String email) {
		return userDao.existsById(email);
	}
	public void createUser(User user) {
		userDao.save(user);
	}
	public boolean isExists(String email, String password) {
		return userDao.existsByEmailAndPassword(email, password);
	}

}
