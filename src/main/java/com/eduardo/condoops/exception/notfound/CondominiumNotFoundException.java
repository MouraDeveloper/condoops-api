package com.eduardo.condoops.exception.notfound;

public class CondominiumNotFoundException extends ResourceNotFoundException {
    public CondominiumNotFoundException(Long id) {
        super("Condominium with ID " + id + " not found.");
    }
}
