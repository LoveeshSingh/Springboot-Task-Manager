package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;

public interface UserService {
	User createUser(String email , String rawPassword);
}