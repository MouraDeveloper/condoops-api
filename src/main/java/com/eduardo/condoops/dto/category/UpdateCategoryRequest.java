package com.eduardo.condoops.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @NotBlank(message = "Name is required")
        @Size(
                max = 80,
                message = "Name must be at most 80 characters long"
        )
        String name,

        @Size(
                max = 255,
                message = "Description must be at most 255 characters long"
        )
        String description
) {
}
