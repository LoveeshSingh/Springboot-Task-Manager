package com.example.taskmanager.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.taskmanager.entity.Task;


public interface TaskService{
	Task createTask(Task task);
    Task getTaskById(Long id);
    Page<Task> getAllTasks(Pageable pageable);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
}
