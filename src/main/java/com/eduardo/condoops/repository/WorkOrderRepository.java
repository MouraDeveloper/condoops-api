package com.eduardo.condoops.repository;

import com.eduardo.condoops.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {
}
