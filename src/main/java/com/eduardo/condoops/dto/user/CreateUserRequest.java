package com.eduardo.condoops.dto.user;

import com.eduardo.condoops.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Name is required.")
        @Size(
                min = 3,
                max = 120,
                message = "Name must be between 3 and 120 characters.")
        String name,


        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        @Size(
                max = 160,
                message = "Email must not exceed 160 characters.")
        String email,


        @NotNull(message = "Role is required.")
        Role role,


        @NotNull(message = "Condominium ID is required.")
        Long condominiumId
) {
}
