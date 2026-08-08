package com.eduardo.condoops.repository;

import com.eduardo.condoops.entity.MaintenanceRequest;
import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import com.eduardo.condoops.entity.enums.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
    Page<MaintenanceRequest> findByCondominiumId(
            Long condominiumId,
            Pageable pageable
    );

    Page<MaintenanceRequest> findByCondominiumIdAndStatus(
            Long condominiumId,
            MaintenanceRequestStatus status,
            Pageable pageable
    );

    Page<MaintenanceRequest> findByCondominiumIdAndPriority(
            Long condominiumId,
            Priority priority,
            Pageable pageable
    );

    Page<MaintenanceRequest> findByCondominiumIdAndAssetId(
            Long condominiumId,
            UUID assetId,
            Pageable pageable
    );

    Page<MaintenanceRequest> findByCondominiumIdAndCategoryId(
            Long condominiumId,
            UUID categoryId,
            Pageable pageable
    );

    Page<MaintenanceRequest> findByCondominiumIdAndOpenedAtBetween(
            Long condominiumId,
            Instant openedAt,
            Instant end,
            Pageable pageable
    );

    Page<MaintenanceRequest> findByCondominiumIdAndDeadlineBeforeAndStatusNotIn(
            Long condominiumId,
            Instant now,
            List<MaintenanceRequestStatus> statuses,
            Pageable pageable
    );
}
