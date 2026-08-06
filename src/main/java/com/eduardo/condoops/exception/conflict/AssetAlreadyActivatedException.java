package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class AssetAlreadyActivatedException extends ResourceConflictException {
    public AssetAlreadyActivatedException(UUID id) {
        super("Asset with id " + id + " is already active.");
    }
}
