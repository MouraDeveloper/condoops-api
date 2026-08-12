package com.eduardo.condoops.entity;

import com.eduardo.condoops.entity.enums.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "work_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 255, nullable = false, unique = true, name = "order_number")
    private String orderNumber;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(unique = true, name = "maintenance_request_id", nullable = false)
    private MaintenanceRequest maintenanceRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private User technician;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "execution_description", columnDefinition = "TEXT")
    private String executionDescription;

    @Column(name = "labor_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal laborCost;

    @Column(name = "material_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal materialCost;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "condominium_id")
    private Condominium condominium;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;


    @Builder
    public WorkOrder(
            String orderNumber,
            MaintenanceRequest maintenanceRequest,
            Condominium condominium
    ) {
        this.orderNumber = orderNumber;
        this.maintenanceRequest = maintenanceRequest;
        this.condominium = condominium;

    }


    @PrePersist
    void prePersist() {
        status = WorkOrderStatus.PENDING_ASSIGNMENT;
        laborCost = BigDecimal.ZERO;
        materialCost = BigDecimal.ZERO;

        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}
