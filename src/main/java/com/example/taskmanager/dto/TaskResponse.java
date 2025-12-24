package com.example.taskmanager.dto;

public class TaskResponse {
	private Long id;
	private String title;
	private String description;
	private Boolean completed;
	
	public TaskResponse(Long id, String title, String description, Boolean completed) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
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
}
