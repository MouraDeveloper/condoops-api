package com.eduardo.condoops.exception.conflict;

public class MissingRejectionReasonException extends ResourceConflictException {
    public MissingRejectionReasonException() {
        super("Missing rejection reason for the maintenance request.");
    }
}
