package com.example.testspring.exception_handlers;

import com.example.testspring.exceptions.IncorrectIdException;
import com.example.testspring.exceptions.OperationFailedException;
import com.example.testspring.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.OperationsException;
import java.lang.module.ResolutionException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<String> incorrectIdExceptionHandler(
            IncorrectIdException exception
    ) {
        return null;
    }

    @ExceptionHandler(OperationsException.class)
    public ResponseEntity<String> operationFailedExceptionHandler(
            OperationFailedException exception
    ) {
        return null;
    }

    @ExceptionHandler(ResolutionException.class)
    public ResponseEntity<String> resourceNotFoundException(
            ResourceNotFoundException exception
    ) {
        return null;
    }
}
