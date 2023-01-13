package com.example.testspring.exception_handlers;

import com.example.testspring.domain.dtos.ErrorDto;
import com.example.testspring.exceptions.IncorrectIdException;
import com.example.testspring.exceptions.OperationFailedException;
import com.example.testspring.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.OperationsException;
import java.lang.module.ResolutionException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<ErrorDto> incorrectIdExceptionHandler(
            IncorrectIdException exception
    ) {
        ErrorDto error = new ErrorDto(
                LocalDateTime.now(),
                exception.getMessage()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ErrorDto> operationFailedExceptionHandler(
            OperationFailedException exception
    ) {
        ErrorDto error = new ErrorDto(
                LocalDateTime.now(),
                exception.getMessage()
        );

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFoundException(
            ResourceNotFoundException exception
    ) {
        ErrorDto error = new ErrorDto(
                LocalDateTime.now(),
                exception.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
