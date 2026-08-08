package com.eduardo.condoops.exception.conflict;

public class CrossCondominiumResourceException extends ResourceConflictException {
    public CrossCondominiumResourceException() {
        super("Resources must belong to the same condominium.");
    }
}
