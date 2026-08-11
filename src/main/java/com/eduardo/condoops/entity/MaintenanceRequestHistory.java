package com.eduardo.condoops.entity;

import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "maintenance_requests_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaintenanceRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "maintenance_request_id", nullable = false)
    private MaintenanceRequest maintenanceRequest;

    @Column(nullable = false, length = 20, name = "previous_status")
    @Enumerated(EnumType.STRING)
    private MaintenanceRequestStatus previousStatus;

    @Column(nullable = false, length = 20, name = "new_status")
    @Enumerated(EnumType.STRING)
    private MaintenanceRequestStatus newStatus;

    private String observation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsible_user_id", nullable = false)
    private User responsibleUser;

    @Column(nullable = false, name = "changed_at")
    private Instant changedAt;


    public MaintenanceRequestHistory(
            MaintenanceRequest maintenanceRequest,
            MaintenanceRequestStatus previousStatus,
            MaintenanceRequestStatus newStatus,
            String observation,
            User responsibleUser
    ) {
        this.maintenanceRequest = maintenanceRequest;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.observation = observation;
        this.responsibleUser = responsibleUser;
    }

    @PrePersist
    void prePersist() {
        this.changedAt = Instant.now();
    }
}
