package com.eduardo.condoops.dto.condominium;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCondominiumRequest(
        @NotBlank(message = "Name is required.")
        @Size(
                min = 3,
                max = 120,
                message = "Name must be between 3 and 120 characters."
        ) String name,


        @NotBlank(message = "Document is required.")
        @Size(
                max = 20,
                message = "Document must not exceed 20 characters."
        ) String document
) {
}
