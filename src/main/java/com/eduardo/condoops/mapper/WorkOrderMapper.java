package com.eduardo.condoops.mapper;

import com.eduardo.condoops.dto.workOrder.WorkOrderResponse;
import com.eduardo.condoops.entity.WorkOrder;

public final class WorkOrderMapper {

    private WorkOrderMapper() {

    }

    public static WorkOrderResponse toResponse(WorkOrder workOrder) {
        return new WorkOrderResponse(
                workOrder.getId(),
                workOrder.getOrderNumber(),
                workOrder.getStatus(),
                workOrder.getMaintenanceRequest().getId(),
                workOrder.getTechnician() != null ? workOrder.getTechnician().getId() : null,
                workOrder.getDiagnosis(),
                workOrder.getExecutionDescription(),
                workOrder.getAssignedAt(),
                workOrder.getStartedAt(),
                workOrder.getFinishedAt(),
                workOrder.getLaborCost(),
                workOrder.getMaterialCost(),
                workOrder.getCondominium().getId(),
                workOrder.getCreatedAt(),
                workOrder.getUpdatedAt()
        );
    }
}
