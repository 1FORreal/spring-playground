package com.example.testspring.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Resource not found!";

    public ResourceNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
