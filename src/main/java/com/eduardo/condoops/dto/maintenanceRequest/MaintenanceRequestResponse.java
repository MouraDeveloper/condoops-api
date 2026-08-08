package com.eduardo.condoops.dto.maintenanceRequest;

import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import com.eduardo.condoops.entity.enums.Priority;

import java.time.Instant;
import java.util.UUID;

public record MaintenanceRequestResponse(
        UUID id,
        String title,
        String description,

        Priority priority,
        MaintenanceRequestStatus status,

        Instant openedAt,
        Instant deadline,

        UUID assetId,
        UUID categoryId,
        UUID requesterId,
        Long condominiumId,

        String rejectionReason,
        Instant completedAt,

        Instant createdAt,
        Instant updatedAt
) {
}
