package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class InactiveCategoryOperationNotAllowedException extends ResourceConflictException {
    public InactiveCategoryOperationNotAllowedException(UUID id) {
        super("Operation not allowed on inactive category with id " + id);
    }
}
