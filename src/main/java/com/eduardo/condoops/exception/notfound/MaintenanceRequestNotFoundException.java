package com.eduardo.condoops.exception.notfound;

public class MaintenanceRequestNotFoundException extends ResourceNotFoundException {
    public MaintenanceRequestNotFoundException(Long id) {
        super("Maintenance request with ID " + id + " not found.");
    }
}
