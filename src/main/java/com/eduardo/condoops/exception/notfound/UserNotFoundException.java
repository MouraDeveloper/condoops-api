package com.eduardo.condoops.exception.notfound;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found.");
    }
}
