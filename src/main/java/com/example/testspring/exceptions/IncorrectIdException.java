package com.example.testspring.exceptions;

public class IncorrectIdException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Incorrect id!";

    public IncorrectIdException() {
        super(ERROR_MESSAGE);
    }
}
