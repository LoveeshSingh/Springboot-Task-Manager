package com.example.taskmanager.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService{
	private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(CreateTaskRequest request) {
        User owner = getCurrentUser();
        Task task = new Task(request.getTitle(), request.getDescription(), owner);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        if (isAdmin()) {
            return taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException(id));
        }
        User owner = getCurrentUser();
        return taskRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable, Boolean completed) {
        if (isAdmin()) {
            if (completed == null) {
                return taskRepository.findAll(pageable);
            }
            return taskRepository.findAllByCompleted(completed, pageable);
        }
        User owner = getCurrentUser();
        if (completed == null) {
            return taskRepository.findAllByOwner(owner, pageable);
        }
        return taskRepository.findAllByOwnerAndCompleted(owner, completed, pageable);
    }

    @Override
    public Task updateTask(Long id, UpdateTaskRequest request) {
        Task existing = getTaskById(id);
        existing.update(request.getTitle(), request.getDescription());
        return taskRepository.save(existing);
    }

    @Override
    public Task markCompleted(Long id){
        Task existing = getTaskById(id);
        existing.markCompleted();
        return taskRepository.save(existing);
    }

    @Override
    public Task reopen(Long id) {
        Task existing = getTaskById(id);
        existing.reopen();
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long id) { 
        Task existing = getTaskById(id);
        taskRepository.delete(existing);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Authentication required"));
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(a -> "ADMIN".equals(a.getAuthority()));
    }
}
