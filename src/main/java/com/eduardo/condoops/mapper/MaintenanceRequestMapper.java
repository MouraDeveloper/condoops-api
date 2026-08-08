package com.eduardo.condoops.mapper;

import com.eduardo.condoops.dto.maintenanceRequest.CreateMaintenanceRequestDto;
import com.eduardo.condoops.dto.maintenanceRequest.MaintenanceRequestResponse;
import com.eduardo.condoops.entity.Asset;
import com.eduardo.condoops.entity.Category;
import com.eduardo.condoops.entity.Condominium;
import com.eduardo.condoops.entity.MaintenanceRequest;
import com.eduardo.condoops.entity.User;

public final class MaintenanceRequestMapper {

    private MaintenanceRequestMapper() {
    }


    public static MaintenanceRequestResponse toResponse(MaintenanceRequest maintenanceRequest) {
        return new MaintenanceRequestResponse(
                maintenanceRequest.getId(),
                maintenanceRequest.getTitle(),
                maintenanceRequest.getDescription(),
                maintenanceRequest.getPriority(),
                maintenanceRequest.getStatus(),
                maintenanceRequest.getOpenedAt(),
                maintenanceRequest.getDeadline(),
                maintenanceRequest.getAsset().getId(),
                maintenanceRequest.getCategory().getId(),
                maintenanceRequest.getRequester().getId(),
                maintenanceRequest.getCondominium().getId(),
                maintenanceRequest.getRejectionReason(),
                maintenanceRequest.getCompletedAt(),
                maintenanceRequest.getCreatedAt(),
                maintenanceRequest.getUpdatedAt()
        );
    }

    public static MaintenanceRequest toEntity(
            CreateMaintenanceRequestDto request,
            Asset asset,
            Category category,
            User requester,
            Condominium condominium
    ) {
        return new MaintenanceRequest(
                request.title(),
                request.description(),
                asset,
                category,
                requester,
                condominium
        );
    }
}
