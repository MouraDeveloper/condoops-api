package com.eduardo.condoops.service;

import com.eduardo.condoops.dto.asset.AssetResponse;
import com.eduardo.condoops.dto.asset.CreateAssetRequest;
import com.eduardo.condoops.dto.asset.UpdateAssetRequest;
import com.eduardo.condoops.dto.asset.UpdateAssetStatusRequest;
import com.eduardo.condoops.entity.Asset;
import com.eduardo.condoops.entity.Condominium;
import com.eduardo.condoops.entity.enums.AssetStatus;
import com.eduardo.condoops.exception.conflict.AssetAlreadyDeactivatedException;
import com.eduardo.condoops.exception.conflict.AssetCodeAlreadyExistsException;
import com.eduardo.condoops.exception.conflict.InactiveAssetOperationNotAllowedException;
import com.eduardo.condoops.exception.conflict.InactiveCondominiumOperationNotAllowedException;
import com.eduardo.condoops.exception.notfound.AssetNotFoundException;
import com.eduardo.condoops.exception.notfound.CondominiumNotFoundException;
import com.eduardo.condoops.mapper.AssetMapper;
import com.eduardo.condoops.repository.AssetRepository;
import com.eduardo.condoops.repository.CondominiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final CondominiumRepository condominiumRepository;


    @Transactional
    public AssetResponse createAsset(
            CreateAssetRequest assetRequest
    ) {

        Condominium condominium = condominiumRepository.findById(assetRequest.condominiumId())
                .orElseThrow(
                        () -> new CondominiumNotFoundException(assetRequest.condominiumId())
                );

        String code = assetRequest.code().strip().toUpperCase(Locale.ROOT);

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(condominium.getId());
        }

        if (assetRepository.existsByCodeAndCondominiumId(code, condominium.getId())) {
            throw new AssetCodeAlreadyExistsException(code, assetRequest.condominiumId());
        }

        Asset asset = AssetMapper.toEntity(assetRequest, code, condominium);

        Asset assetSaved = assetRepository.save(asset);

        return AssetMapper.toResponse(assetSaved);
    }


    @Transactional(readOnly = true)
    public AssetResponse findById(UUID id) {
        return assetRepository.findById(id)
                .filter(Asset::isActive)
                .map(AssetMapper::toResponse)
                .orElseThrow(
                        () -> new AssetNotFoundException(id)
                );
    }


    @Transactional(readOnly = true)
    public Page<AssetResponse> findAll(boolean active, Pageable pageable) {
        return assetRepository.findByActive(active, pageable)
                .map(AssetMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<AssetResponse> findAllByCondominiumIdAndActive(Long id, boolean active, Pageable pageable) {

        Condominium condominium = condominiumRepository.findById(id)
                .orElseThrow(
                        () -> new CondominiumNotFoundException(id)
                );

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(condominium.getId());
        }

        return assetRepository
                .findByActiveAndCondominiumId(active, condominium.getId(), pageable)
                .map(AssetMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<AssetResponse> findAllByCondominiumIdAndAssetStatus(
            Long id,
            AssetStatus assetStatus,
            Pageable pageable
    ) {

        Condominium condominium = condominiumRepository.findById(id)
                .orElseThrow(
                        () -> new CondominiumNotFoundException(id)
                );

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(condominium.getId());
        }

        return assetRepository.findByCondominiumIdAndAssetStatus(condominium.getId(), assetStatus, pageable)
                .map(AssetMapper::toResponse);
    }


    @Transactional
    public AssetResponse updateAsset(UUID id, UpdateAssetRequest updateRequest) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(
                        () -> new AssetNotFoundException(id)
                );

        if (!asset.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(asset.getCondominium().getId());
        }

        if (!asset.isActive()) {
            throw new InactiveAssetOperationNotAllowedException(id);
        }

        String code = updateRequest.code().strip().toUpperCase(Locale.ROOT);

        if (assetRepository.existsByCodeAndCondominiumIdAndIdNot(
                code, asset.getCondominium().getId(), id
        )) {
            throw new AssetCodeAlreadyExistsException(code, asset.getCondominium().getId());
        }

        asset.updateData(
                code,
                updateRequest.name(),
                updateRequest.description(),
                updateRequest.location(),
                updateRequest.manufacturer(),
                updateRequest.model(),
                updateRequest.serialNumber()
        );

        assetRepository.flush();

        return AssetMapper.toResponse(asset);
    }

    @Transactional
    public AssetResponse updateAssetStatus(UUID id, UpdateAssetStatusRequest updateRequest) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(
                        () -> new AssetNotFoundException(id)
                );

        if (!asset.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(
                    asset.getCondominium()
                            .getId()
            );
        }

        if (!asset.isActive()) {
            throw new InactiveAssetOperationNotAllowedException(id);
        }

        if (asset.getAssetStatus() != updateRequest.status()) {
            asset.updateStatus(updateRequest.status());
            assetRepository.flush();
        }

        return AssetMapper.toResponse(asset);
    }

    @Transactional
    public AssetResponse deactivateAsset(UUID id) {

        Asset asset = assetRepository.findById(id)
                .orElseThrow(
                        () -> new AssetNotFoundException(id)
                );

        if (!asset.isActive()) {
            throw new AssetAlreadyDeactivatedException(id);
        }

        asset.deactivate();
        assetRepository.flush();

        return AssetMapper.toResponse(asset);
    }
}
