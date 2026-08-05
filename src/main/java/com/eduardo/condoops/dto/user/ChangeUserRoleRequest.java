package com.eduardo.condoops.dto.user;

import com.eduardo.condoops.entity.enums.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeUserRoleRequest(
        @NotNull(message = "Role is required.")
        Role role
) {
}
