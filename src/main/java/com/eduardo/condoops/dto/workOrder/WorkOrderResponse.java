package com.eduardo.condoops.dto.workOrder;

import com.eduardo.condoops.entity.enums.WorkOrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record WorkOrderResponse(
        UUID id,
        String orderNumber,
        WorkOrderStatus status,
        UUID maintenanceRequestId,
        UUID technicianId,
        String diagnosis,
        String executionDescription,
        Instant assignedAt,
        Instant startedAt,
        Instant finishedAt,
        BigDecimal laborCost,
        BigDecimal materialCost,
        Long condominiumId,
        Instant createdAt,
        Instant updatedAt
) {
}
