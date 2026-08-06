package com.eduardo.condoops.exception.conflict;

public class AssetCodeAlreadyExistsException extends ResourceConflictException {
    public AssetCodeAlreadyExistsException(String code, Long condominiumId) {
        super("Asset with code '" + code + "' already exists in condominium with id: " + condominiumId);
    }
}
