package com.eduardo.condoops.dto.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAssetRequest(
        @NotBlank(message = "Code is required")
        @Size(
                max = 50,
                message = "Code must not exceed 50 characters"
        )
        String code,

        @NotBlank(message = "Name is required")
        @Size(
                max = 120,
                message = "Name must not exceed 120 characters"
        )
        String name,

        @Size(
                max = 500,
                message = "Description must not exceed 500 characters"
        )
        String description,

        @NotBlank(message = "Location is required")
        @Size(
                max = 160,
                message = "Location must not exceed 160 characters"
        )
        String location,

        @Size(
                max = 120,
                message = "Manufacturer must not exceed 120 characters"
        )
        String manufacturer,

        @Size(
                max = 100,
                message = "Model must not exceed 100 characters"
        )
        String model,

        @Size(
                max = 100,
                message = "Serial number must not exceed 100 characters"
        )
        String serialNumber

) {
}
