package com.eduardo.condoops.exception.conflict;

import java.util.UUID;

public class CategoryAlreadyActivatedException extends ResourceConflictException {
    public CategoryAlreadyActivatedException(UUID id) {
      super("Category " + id + " already activated");
    }
}
