package com.eduardo.condoops.dto.category;

import java.time.Instant;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description,
        int defaultResponseHours,
        boolean active,
        Long condominiumId,
        Instant createdAt,
        Instant updatedAt
) {
}
