package com.eduardo.condoops.controller;

import com.eduardo.condoops.dto.asset.AssetResponse;
import com.eduardo.condoops.dto.asset.CreateAssetRequest;
import com.eduardo.condoops.dto.asset.UpdateAssetRequest;
import com.eduardo.condoops.dto.asset.UpdateAssetStatusRequest;
import com.eduardo.condoops.entity.enums.AssetStatus;
import com.eduardo.condoops.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;


    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(
            @RequestBody @Valid CreateAssetRequest createAssetRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                assetService.createAsset(createAssetRequest)
        );
    }


    @GetMapping
    public ResponseEntity<Page<AssetResponse>> findAll(
            @RequestParam boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(assetService.findAll(active, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(assetService.findById(id));
    }

    @GetMapping("/condominium/{condominiumId}")
    public ResponseEntity<Page<AssetResponse>> findAllByCondominiumIdAndActive(
            @PathVariable Long condominiumId,
            @RequestParam boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(assetService.findAllByCondominiumIdAndActive(
                condominiumId,
                active,
                pageable
        ));
    }

    @GetMapping("/condominium/{condominiumId}/status")
    public ResponseEntity<Page<AssetResponse>> findAllByCondominiumIdAndAssetStatus(
            @PathVariable Long condominiumId,
            @RequestParam(name = "status") AssetStatus assetStatus,
            Pageable pageable
    ) {
        return ResponseEntity.ok(assetService.findAllByCondominiumIdAndAssetStatus(
                condominiumId,
                assetStatus,
                pageable
        ));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateAssetRequest updateAssetRequest
    ) {
        return ResponseEntity.ok(assetService.updateAsset(
                id,
                updateAssetRequest
        ));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AssetResponse> updateAssetStatus(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateAssetStatusRequest updateAssetStatusRequest
    ) {
        return ResponseEntity.ok(assetService.updateAssetStatus(
                id,
                updateAssetStatusRequest
        ));
    }



    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AssetResponse> deactivateAsset(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(assetService.deactivateAsset(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<AssetResponse> activateAsset(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(assetService.activateAsset(id));
    }
}
