package com.eduardo.condoops.dto.maintenanceRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMaintenanceRequestRequest(
        @NotBlank(message = "Title is required")
        @Size(
                min = 5,
                max = 120,
                message = "Title must be between 5 and 120 characters"
        )
        String title,

        @NotBlank(message = "Description is required")
        @Size(
                min = 10,
                max = 1000,
                message = "Description must be between 10 and 1000 characters"
        )
        String description
) {
}
