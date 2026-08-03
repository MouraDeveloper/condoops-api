package com.eduardo.condoops.exception.conflict;

public class CondominiumDocumentAlreadyExistsException extends ResourceConflictException {
    public CondominiumDocumentAlreadyExistsException(String document) {
        super("Condominium with document " + document + " already exists.");
    }
}
