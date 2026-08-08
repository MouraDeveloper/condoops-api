package com.eduardo.condoops.dto.maintenanceRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateMaintenanceRequestDto(
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
        String description,

        @NotNull(message = "Asset ID is required")
        UUID assetId,

        @NotNull(message = "Category ID is required")
        UUID categoryId,

        @NotNull(message = "Requester ID is required")
        UUID requesterId
) {
}
