package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class CategoryAlreadyDeactivatedException extends ResourceConflictException {
    public CategoryAlreadyDeactivatedException(UUID id) {
        super("Category with ID " + id + " is already deactivated.");
    }
}
