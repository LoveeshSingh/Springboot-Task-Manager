package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {
	@NotBlank(message = "Title must not be blank") 
	@Size(max = 200, message = "Title must be at most 200 characters")
	private String title;
	@Size(max = 2000, message = "Description must be at most 2000 characters")
	private String description; 

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
