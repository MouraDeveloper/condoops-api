package com.eduardo.condoops.exception.notfound;

public class AssetNotFoundException extends ResourceNotFoundException {
    public AssetNotFoundException(Long id) {
        super("Asset with ID " + id + " not found.");
    }
}
