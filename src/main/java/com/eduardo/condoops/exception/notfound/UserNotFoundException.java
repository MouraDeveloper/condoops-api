package com.eduardo.condoops.exception.notfound;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(UUID id) {
        super("User with ID " + id + " not found.");
    }
}
