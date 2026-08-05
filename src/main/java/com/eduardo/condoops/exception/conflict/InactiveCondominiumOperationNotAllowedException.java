package com.eduardo.condoops.exception.conflict;

public class InactiveCondominiumOperationNotAllowedException extends ResourceConflictException {
    public InactiveCondominiumOperationNotAllowedException(Long id) {
        super("Operation not allowed for inactive condominium with ID: " + id);
    }
}
