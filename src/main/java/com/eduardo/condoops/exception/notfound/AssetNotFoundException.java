package com.eduardo.condoops.exception.notfound;

import java.util.UUID;

public class AssetNotFoundException extends ResourceNotFoundException {
    public AssetNotFoundException(UUID id) {
        super("Asset with ID " + id + " not found.");
    }
}
