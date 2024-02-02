package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserDao extends JpaRepository<User, String>{

	boolean existsByEmailAndPassword(String email, String password);

}
