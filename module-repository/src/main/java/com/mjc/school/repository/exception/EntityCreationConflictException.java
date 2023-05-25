package com.mjc.school.repository.exception;

public class EntityCreationConflictException extends RuntimeException {
    public EntityCreationConflictException(String message) {
        super(message);
    }

    public EntityCreationConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityCreationConflictException(Throwable cause) {
        super(cause);
    }
}
