package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/tasks")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService){
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
		Task saved = taskService.createTask(TaskMapper.toEntity(request));
		return ResponseEntity.ok(
            TaskMapper.toResponse(saved)
    	);
	}

	@GetMapping
	public ResponseEntity<List<TaskResponse>> getAllTasks() {

		List<TaskResponse> response = taskService.getAllTasks()
				.stream()
				.map(t -> TaskMapper.toResponse(t))
				.toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {

		return ResponseEntity.ok(
				TaskMapper.toResponse(
						taskService.getTaskById(id)
				)
		);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(
			@PathVariable Long id,
			@Valid @RequestBody UpdateTaskRequest request) {
		Task updated = taskService.updateTask(id, TaskMapper.toEntity(request));

		return ResponseEntity.ok(
				TaskMapper.toResponse(updated)
		);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
