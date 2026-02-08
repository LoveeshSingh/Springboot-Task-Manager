package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.PagedResponse;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	private static final int MAX_PAGE_SIZE = 50;
	private final TaskService taskService;

	public TaskController(TaskService taskService){
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
		var saved = taskService.createTask(request);
		return ResponseEntity.ok(
            TaskMapper.toResponse(saved)
    	);
	}

	@GetMapping
	public ResponseEntity<PagedResponse<TaskResponse>> getAllTasks(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc") String direction,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) Boolean completed) {

		int pageSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

		Sort sort = direction.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, pageSize, sort);

		Page<com.example.taskmanager.entity.Task> taskPage = taskService.getAllTasks(pageable, completed);

		List<TaskResponse> content = taskPage
				.getContent()
				.stream()
				.map(TaskMapper::toResponse)
				.toList();

		PagedResponse<TaskResponse> response =
				new PagedResponse<>(
						content,
						taskPage.getNumber(),
						taskPage.getTotalPages(),
						taskPage.getTotalElements(),
						taskPage.isLast()
				);

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
		var updated = taskService.updateTask(id, request);

		return ResponseEntity.ok(TaskMapper.toResponse(updated));
	}

	@PutMapping("/{id}/complete")
	public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id) {
		var updated = taskService.markCompleted(id);
		return ResponseEntity.ok(TaskMapper.toResponse(updated));
	}

	@PutMapping("/{id}/reopen")
	public ResponseEntity<TaskResponse> reopenTask(@PathVariable Long id) {
		var updated = taskService.reopen(id);
		return ResponseEntity.ok(TaskMapper.toResponse(updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
