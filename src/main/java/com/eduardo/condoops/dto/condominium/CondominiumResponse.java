package com.eduardo.condoops.dto.condominium;

import java.time.Instant;

public record CondominiumResponse(
        Long id,
        String name,
        String document,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}
