package com.eduardo.condoops.exception.conflict;

public abstract class ResourceConflictException extends RuntimeException {
    protected ResourceConflictException(String message) {
        super(message);
    }
}
