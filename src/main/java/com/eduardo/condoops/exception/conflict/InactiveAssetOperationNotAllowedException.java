package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class InactiveAssetOperationNotAllowedException extends ResourceConflictException {
    public InactiveAssetOperationNotAllowedException(UUID id) {
        super("Inactive asset operation for id " + id);
    }
}
