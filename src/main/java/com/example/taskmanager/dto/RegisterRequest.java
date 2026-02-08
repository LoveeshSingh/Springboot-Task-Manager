package com.example.taskmanager.dto;

import com.example.taskmanager.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	@Email(message = "Email must be valid")
	@NotBlank(message = "Email must not be blank")
	private String email;

	@NotBlank(message = "Password must not be blank")
	@Size(min = 8, max = 100, message = "Password must be 8 to 100 characters")
	private String password;

	@NotNull(message = "Role must not be null")
	private Role role;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
