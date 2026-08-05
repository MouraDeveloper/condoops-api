package com.eduardo.condoops.exception.conflict;

public class UserEmailAlreadyExistsException extends ResourceConflictException {
    public UserEmailAlreadyExistsException(String email) {
        super("User with email " + email + " already exists.");
    }
}
