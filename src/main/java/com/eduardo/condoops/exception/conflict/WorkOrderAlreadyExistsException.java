package com.eduardo.condoops.exception.conflict;

public class WorkOrderAlreadyExistsException extends ResourceConflictException {
    public WorkOrderAlreadyExistsException() {
        super("Work order already exists for this maintenance request.");
    }
}
