package com.eduardo.condoops.exception.conflict;

public class InvalidMaintenanceRequestStatusException extends ResourceConflictException {
    public InvalidMaintenanceRequestStatusException() {
        super("Invalid maintenance request status.");
    }
}
