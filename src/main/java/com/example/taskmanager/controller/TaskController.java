package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.entity.Task;
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
		Task task = new Task();
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setCompleted(request.getCompleted());

		Task saved = taskService.createTask(task);
		return ResponseEntity.ok(
            new TaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCompleted()
            )
    	);
	}

	@GetMapping
	public ResponseEntity<List<TaskResponse>> getAllTasks() {

		List<TaskResponse> response = taskService.getAllTasks()
				.stream()
				.map(t -> new TaskResponse(
						t.getId(),
						t.getTitle(),
						t.getDescription(),
						t.getCompleted()
				))
				.toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {

		Task t = taskService.getTaskById(id);

		return ResponseEntity.ok(
				new TaskResponse(
						t.getId(),
						t.getTitle(),
						t.getDescription(),
						t.getCompleted()
				)
		);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(
			@PathVariable Long id,
			@Valid @RequestBody UpdateTaskRequest request) {

		Task task = new Task();
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setCompleted(request.getCompleted());

		Task updated = taskService.updateTask(id, task);

		return ResponseEntity.ok(
				new TaskResponse(
						updated.getId(),
						updated.getTitle(),
						updated.getDescription(),
						updated.getCompleted()
				)
		);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
