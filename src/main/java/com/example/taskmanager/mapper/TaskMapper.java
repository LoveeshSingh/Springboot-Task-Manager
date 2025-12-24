package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.entity.Task;

public final class TaskMapper {

	private TaskMapper(){}

	public static Task toEntity(CreateTaskRequest request){
		Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.getCompleted());
        return task;
	}

	public static Task toEntity(UpdateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.getCompleted());
        return task;
    }

	public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCompleted()
        );
    }

}
