package com.eduardo.condoops.mapper;

import com.eduardo.condoops.dto.asset.AssetResponse;
import com.eduardo.condoops.dto.asset.CreateAssetRequest;
import com.eduardo.condoops.entity.Asset;
import com.eduardo.condoops.entity.Condominium;

public final class AssetMapper {

    public static AssetResponse toResponse(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getCode(),
                asset.getName(),
                asset.getDescription(),
                asset.getLocation(),
                asset.getManufacturer(),
                asset.getModel(),
                asset.getSerialNumber(),
                asset.getAssetStatus(),
                asset.isActive(),
                asset.getCondominium().getId(),
                asset.getCondominium().getName(),
                asset.getCreatedAt(),
                asset.getUpdatedAt()
        );
    }

    public static Asset toEntity(CreateAssetRequest request, String code, Condominium condominium) {
        return new Asset(
                code,
                request.name(),
                request.description(),
                request.location(),
                request.manufacturer(),
                request.model(),
                request.serialNumber(),
                condominium
        );
    }
}
