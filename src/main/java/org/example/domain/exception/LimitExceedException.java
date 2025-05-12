package org.example.domain.exception;

public class LimitExceedException extends RuntimeException {
    public LimitExceedException(String message) {
        super(message);
    }
}