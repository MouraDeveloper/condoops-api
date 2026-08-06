package com.eduardo.condoops.repository;

import com.eduardo.condoops.entity.Asset;
import com.eduardo.condoops.entity.enums.AssetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
    boolean existsByCodeAndCondominiumId(
            String code,
            Long condominiumId
    );

    boolean existsByCodeAndCondominiumIdAndIdNot(
            String code,
            Long condominiumId,
            UUID id
    );

    Page<Asset> findByActive(
            boolean active,
            Pageable pageable
    );

    Page<Asset> findByActiveAndCondominiumId(
            boolean active,
            Long condominiumId,
            Pageable pageable
    );

    Page<Asset> findByCondominiumId(
            Long condominiumId,
            Pageable pageable
    );

    Page<Asset> findByCondominiumIdAndAssetStatus(
            Long condominiumId,
            AssetStatus assetStatus,
            Pageable pageable
    );

}
