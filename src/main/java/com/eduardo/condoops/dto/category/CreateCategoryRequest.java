package com.eduardo.condoops.dto.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
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
        String description,

        @Min(value = 1, message = "Default response hours must be a positive integer")
        int defaultResponseHours,

        @NotNull(message = "Condominium is required")
        Long condominiumId
) {
}
