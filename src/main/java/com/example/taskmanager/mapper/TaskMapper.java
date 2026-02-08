package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.entity.Task;

public final class TaskMapper {

	private TaskMapper(){}

	public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

}
