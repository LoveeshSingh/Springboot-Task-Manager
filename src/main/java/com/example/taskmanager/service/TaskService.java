package com.example.taskmanager.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.entity.Task;


public interface TaskService{
	Task createTask(CreateTaskRequest request);
    Task getTaskById(Long id);
    Page<Task> getAllTasks(Pageable pageable, Boolean completed);
    Task updateTask(Long id, UpdateTaskRequest request);
    Task markCompleted(Long id);
    Task reopen(Long id);
    void deleteTask(Long id);
}
