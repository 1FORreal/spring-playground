package com.example.testspring.exceptions;

public class OperationFailedException extends RuntimeException{
    private static final String ERROR_MESSAGE = "Operation failed!";

    public OperationFailedException() {
        super(ERROR_MESSAGE);
    }

}
