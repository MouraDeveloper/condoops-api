package com.eduardo.condoops.entity;

import com.eduardo.condoops.entity.enums.AssetStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "assets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 160)
    private String location;

    @Column(length = 120)
    private String manufacturer;

    @Column(length = 100)
    private String model;

    @Column(length = 100, name = "serial_number")
    private String serialNumber;

    @Column(nullable = false, name = "status", length = 30)
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus = AssetStatus.OPERATIONAL;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "condominium_id")
    private Condominium condominium;

    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;


    @Builder
    public Asset(
            String code,
            String name,
            String description,
            String location,
            String manufacturer,
            String model,
            String serialNumber,
            Condominium condominium
    ) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.location = location;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNumber = serialNumber;
        this.condominium = condominium;
    }


    public void deactivate() {
        if (this.active) {
            this.active = false;
        }
    }

    public void activate() {
        if (!this.active) {
            this.active = true;
        }
    }

    public void updateData(
            String code,
            String name,
            String description,
            String location,
            String manufacturer,
            String model,
            String serialNumber
    ) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.location = location;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNumber = serialNumber;
    }

    public void updateStatus(
            AssetStatus assetStatus
    ) {
        this.assetStatus = assetStatus;
    }


    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
