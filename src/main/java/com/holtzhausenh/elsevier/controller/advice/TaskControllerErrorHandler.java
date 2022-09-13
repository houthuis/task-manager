package com.holtzhausenh.elsevier.controller.advice;

import com.holtzhausenh.elsevier.exception.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class TaskControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TaskException.class)
    protected ResponseEntity<Object> handleTaskException(TaskException exception) {
        log.error("Failed to process request", exception);
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleTaskException(Exception exception) {
        log.error("Failed to process request", exception);
        return ResponseEntity.internalServerError().body("Failed to process request");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Field validation failed", ex);

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
