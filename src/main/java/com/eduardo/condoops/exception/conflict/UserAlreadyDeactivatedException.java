package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class UserAlreadyDeactivatedException extends ResourceConflictException {
    public UserAlreadyDeactivatedException(UUID id) {
        super("User with ID " + id + " is already deactivated.");
    }
}
