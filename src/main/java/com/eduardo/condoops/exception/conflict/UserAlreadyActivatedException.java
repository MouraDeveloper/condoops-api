package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class UserAlreadyActivatedException extends ResourceConflictException {
    public UserAlreadyActivatedException(UUID id) {
        super("User with ID " + id + " is already activated.");
    }
}
