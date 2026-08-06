package com.eduardo.condoops.exception.conflict;

public class CategoryNameAlreadyExistsException extends ResourceConflictException {
    public CategoryNameAlreadyExistsException(String name, Long condominiumId) {
        super(String.format("Category with name '%s' already exists in condominium with ID '%d'.", name, condominiumId));
    }
}
