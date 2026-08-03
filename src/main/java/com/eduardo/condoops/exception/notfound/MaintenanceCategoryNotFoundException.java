package com.eduardo.condoops.exception.notfound;

public class MaintenanceCategoryNotFoundException extends ResourceNotFoundException {
    public MaintenanceCategoryNotFoundException(Long id) {
        super("Maintenance category with ID " + id + " not found.");
    }
}
