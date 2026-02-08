package com.example.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmanager.entity.Role;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.UserAlreadyExistsException;
import com.example.taskmanager.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService{
	public final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder; 

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createUser(String email, String rawPassword, Role role){
		if (userRepository.existsByEmail(email)) {
			throw new UserAlreadyExistsException(email);
		}
		String hashedPassword = passwordEncoder.encode(rawPassword);
		User user = new User(email, hashedPassword,role);
		return userRepository.save(user);
	}
}
