package com.eduardo.condoops.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "Name is required.")
        @Size(
                min = 3,
                max = 120,
                message = "Name must be between 3 and 120 characters."
        ) String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be a valid email address.")
        @Size(
                max = 160,
                message = "Email must not exceed 160 characters."
        )
        String email
) {
}
