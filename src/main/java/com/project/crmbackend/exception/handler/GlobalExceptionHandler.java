package com.project.crmbackend.exception.handler;

import com.project.crmbackend.dto.response.ErrorResponse;
import com.project.crmbackend.exception.AlreadyExistsException;
import com.project.crmbackend.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse alreadyExistExceptionHandler(AlreadyExistsException exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm:ss")))
                .status(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm:ss")))
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, Object> exceptionResponse = new LinkedHashMap<>();
        exceptionResponse.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm:ss")));
        exceptionResponse.put("status", HttpStatus.BAD_REQUEST.value());
        exceptionResponse.put("path", request.getRequestURI());

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            exceptionResponse.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception exception, HttpServletRequest request) {
        exception.printStackTrace();
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm:ss")))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
