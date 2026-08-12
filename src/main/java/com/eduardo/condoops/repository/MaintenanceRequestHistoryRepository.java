package com.eduardo.condoops.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaintenanceRequestHistoryRepository extends JpaRepository<com.eduardo.condoops.entity.MaintenanceRequestHistory, UUID> {
    Page<com.eduardo.condoops.entity.MaintenanceRequestHistory> findByMaintenanceRequest_IdOrderByChangedAtAsc(
            UUID maintenanceRequestId,
            Pageable pageable
    );
}
