package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class AssetAlreadyDeactivatedException extends ResourceConflictException {
    public AssetAlreadyDeactivatedException(UUID id) {
        super("Asset with ID " + id + " is already deactivated.");
    }
}
