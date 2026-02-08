package com.example.taskmanager.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.dto.LoginRequest;
import com.example.taskmanager.dto.RegisterRequest;
import com.example.taskmanager.security.JwtService;
import com.example.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthController(
			UserService userService,
			AuthenticationManager authenticationManager,
			JwtService jwtService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
		userService.createUser(request.getEmail(),request.getPassword(),request.getRole());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        String token = jwtService.generateToken((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
