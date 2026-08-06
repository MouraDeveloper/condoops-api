package com.eduardo.condoops.dto.asset;

import com.eduardo.condoops.entity.enums.AssetStatus;

import java.time.Instant;
import java.util.UUID;

public record AssetResponse(
        UUID id,
        String code,
        String name,
        String description,
        String location,
        String manufacturer,
        String model,
        String serialNumber,
        AssetStatus status,
        boolean active,
        Long condominiumId,
        String condominiumName,
        Instant createdAt,
        Instant updatedAt
) {
}
