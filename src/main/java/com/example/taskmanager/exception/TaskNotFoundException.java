package com.example.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {
	public TaskNotFoundException(long id){
		super("Task not found with id: " + id);
	}
}
