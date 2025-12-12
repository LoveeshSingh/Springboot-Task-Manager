package com.example.taskmanager.service;

import java.util.List;

import com.example.taskmanager.entity.Task;


public interface TaskService{
	Task createTask(Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
}
