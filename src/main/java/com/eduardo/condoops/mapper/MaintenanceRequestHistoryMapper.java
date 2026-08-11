package com.eduardo.condoops.mapper;


import com.eduardo.condoops.dto.maintenanceRequestHistory.MaintenanceRequestHistoryResponse;
import com.eduardo.condoops.entity.MaintenanceRequestHistory;

public final class MaintenanceRequestHistoryMapper {

    private MaintenanceRequestHistoryMapper() {

    }

    public static MaintenanceRequestHistoryResponse toResponse(
            MaintenanceRequestHistory requestHistory
    ) {
        return new MaintenanceRequestHistoryResponse(
                requestHistory.getId(),
                requestHistory.getMaintenanceRequest().getId(),
                requestHistory.getPreviousStatus(),
                requestHistory.getNewStatus(),
                requestHistory.getObservation(),
                requestHistory.getResponsibleUser().getId(),
                requestHistory.getChangedAt()
        );
    }
}
