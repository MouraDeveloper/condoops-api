package com.eduardo.condoops.exception.notfound;

public abstract class ResourceNotFoundException extends RuntimeException {
    protected ResourceNotFoundException(String message) {
        super(message);
    }
}
