package com.eduardo.condoops.exception.notfound;

import com.eduardo.condoops.exception.conflict.ResourceConflictException;

import java.util.UUID;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(UUID id) {
        super("Category with id " + id + " not found");
    }
}
