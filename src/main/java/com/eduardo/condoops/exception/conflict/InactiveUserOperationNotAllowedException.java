package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class InactiveUserOperationNotAllowedException extends ResourceConflictException {
    public InactiveUserOperationNotAllowedException(UUID id) {
        super("Operation not allowed on inactive user: " + id);
    }
}
