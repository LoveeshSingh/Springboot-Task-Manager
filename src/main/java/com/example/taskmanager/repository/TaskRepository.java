package com.example.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;

public interface TaskRepository extends JpaRepository<Task,Long>{
	Page<Task> findAllByOwner(User owner, Pageable pageable);
	Page<Task> findAllByOwnerAndCompleted(User owner, boolean completed, Pageable pageable);
	Page<Task> findAllByCompleted(boolean completed, Pageable pageable);
	Optional<Task> findByIdAndOwner(Long id, User owner);
	boolean existsByIdAndOwner(Long id, User owner);
}
