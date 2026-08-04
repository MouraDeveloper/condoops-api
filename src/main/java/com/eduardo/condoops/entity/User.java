package com.eduardo.condoops.entity;

import com.eduardo.condoops.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Role role;

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
    public User(String name, String email, Role role, Condominium condominium) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.condominium = condominium;
    }


    public void updateData(
            String name, String email, Role role
    ) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    @PrePersist
    private void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
