package com.eduardo.condoops.exception.conflict;

public class InactiveMaintenanceRequestResourceException extends ResourceConflictException {
    public InactiveMaintenanceRequestResourceException() {
        super("Asset, user, and category must be active to create a maintenance request.");
    }
}
