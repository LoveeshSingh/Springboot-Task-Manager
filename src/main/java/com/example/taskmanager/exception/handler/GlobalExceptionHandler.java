package com.example.taskmanager.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import com.example.taskmanager.dto.ErrorResponse;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.exception.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler{ 
	
	@ExceptionHandler(TaskNotFoundException.class)	
	public ResponseEntity<ErrorResponse> handleTaskNotFound(
			TaskNotFoundException ex,
			HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(
						HttpStatus.NOT_FOUND.value(),
						"Not Found",
						ex.getMessage(),
						request.getRequestURI()
				));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
			MethodArgumentNotValidException ex,
			HttpServletRequest request) {
        String error = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
						HttpStatus.BAD_REQUEST.value(),
						"Bad Request",
						error,
						request.getRequestURI()
				));
    }

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserExists(
			UserAlreadyExistsException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(
						HttpStatus.CONFLICT.value(),
						"Conflict",
						ex.getMessage(),
						request.getRequestURI()
				));
	}

	@ExceptionHandler({AuthenticationCredentialsNotFoundException.class, BadCredentialsException.class})
	public ResponseEntity<ErrorResponse> handleAuthErrors(
			RuntimeException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse(
						HttpStatus.UNAUTHORIZED.value(),
						"Unauthorized",
						"Authentication failed",
						request.getRequestURI()
				));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(
			Exception ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						"Internal Server Error",
						"Something went wrong",
						request.getRequestURI()
				));
	}
}
