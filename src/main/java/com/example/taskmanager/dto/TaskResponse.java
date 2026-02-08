package com.example.taskmanager.dto;

import java.time.LocalDateTime;

public class TaskResponse {
	private Long id;
	private String title;
	private String description;
	private Boolean completed;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public TaskResponse(
			Long id,
			String title,
			String description,
			Boolean completed,
			LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public Boolean getCompleted() {
		return completed;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
