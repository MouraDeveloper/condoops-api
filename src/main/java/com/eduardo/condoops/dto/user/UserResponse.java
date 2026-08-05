package com.eduardo.condoops.dto.user;

import com.eduardo.condoops.entity.enums.Role;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        Role role,
        boolean active,
        Long condominiumId,
        String condominiumName,
        Instant createdAt,
        Instant updatedAt
) {
}
