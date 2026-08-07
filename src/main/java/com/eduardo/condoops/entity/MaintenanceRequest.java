package com.eduardo.condoops.entity;

import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import com.eduardo.condoops.entity.enums.Priority;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table(name = "maintenance_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private MaintenanceRequestStatus status;

    @Column(nullable = false, updatable = false)
    private Instant openedAt;

    @Column(nullable = false, updatable = false)
    private Instant deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominium_id", nullable = false)
    private Condominium condominium;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public MaintenanceRequest(
            String title,
            String description,
            Asset asset,
            Category category,
            User requester,
            Condominium condominium
    ) {
        this.title = title;
        this.description = description;
        this.asset = asset;
        this.category = category;
        this.requester = requester;
        this.condominium = condominium;
    }


    public void sendToReview() {
        this.status = MaintenanceRequestStatus.UNDER_REVIEW;
    }

    public void complete() {
        this.completedAt = Instant.now();
        this.status = MaintenanceRequestStatus.COMPLETED;
    }

    public void reject(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        this.status = MaintenanceRequestStatus.REJECTED;
    }

    public void approve() {
        this.status = MaintenanceRequestStatus.APPROVED;
    }

    public void cancel() {
        this.status = MaintenanceRequestStatus.CANCELED;
    }

    public void update(
            String title,
            String description
    ) {
        this.title = title;
        this.description = description;
    }

    public void changePriority(Priority priority) {
        this.priority = priority;
    }

    private Instant calculateDeadline() {
        return openedAt.plus(Duration.ofHours(category.getDefaultResponseHours()));
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.openedAt = now;
        this.status = MaintenanceRequestStatus.OPEN;
        this.priority = Priority.LOW;
        this.deadline = calculateDeadline();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
