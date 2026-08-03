package com.eduardo.condoops.exception.notfound;

public class WorkOrderNotFoundException extends ResourceNotFoundException {
    public WorkOrderNotFoundException(Long id) {
        super("Work order with ID " + id + " not found.");
    }
}
