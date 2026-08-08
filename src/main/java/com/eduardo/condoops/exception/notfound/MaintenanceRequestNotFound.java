package com.eduardo.condoops.exception.notfound;

import java.util.UUID;

public class MaintenanceRequestNotFound extends ResourceNotFoundException {
    public MaintenanceRequestNotFound(UUID id) {
        super("Maintenance request not found with ID: " + id);
    }
}
