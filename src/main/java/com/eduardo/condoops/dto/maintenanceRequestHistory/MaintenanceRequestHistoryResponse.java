package com.eduardo.condoops.dto.maintenanceRequestHistory;

import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;

import java.time.Instant;
import java.util.UUID;

public record MaintenanceRequestHistoryResponse(
        UUID id,
        UUID maintenanceRequestId,
        MaintenanceRequestStatus previousStatus,
        MaintenanceRequestStatus newStatus,
        String observation,
        UUID responsibleUserId,
        Instant changedAt
) {
}
