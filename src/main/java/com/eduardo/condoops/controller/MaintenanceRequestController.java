package com.eduardo.condoops.controller;

import com.eduardo.condoops.dto.maintenanceRequest.CreateMaintenanceRequestDto;
import com.eduardo.condoops.dto.maintenanceRequest.MaintenanceRequestResponse;
import com.eduardo.condoops.dto.maintenanceRequestHistory.MaintenanceRequestHistoryResponse;
import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import com.eduardo.condoops.entity.enums.Priority;
import com.eduardo.condoops.service.MaintenanceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/maintenance-requests")
@RequiredArgsConstructor
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;


    @PostMapping("/{condominiumId}")
    public ResponseEntity<MaintenanceRequestResponse> createMaintenance(
            @RequestBody @Valid CreateMaintenanceRequestDto createMaintenanceRequestDto,
            @PathVariable Long condominiumId
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.createMaintenance(
                        createMaintenanceRequestDto,
                        condominiumId
                )
        );
    }


    @PutMapping("/{requestId}/condominium/{condominiumId}/send-to-review")
    public ResponseEntity<MaintenanceRequestResponse> sendToReview(
            @PathVariable UUID requestId,
            @PathVariable Long condominiumId,
            @RequestParam UUID responsibleUserId
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.sendToReview(
                        requestId,
                        condominiumId,
                        responsibleUserId
                )
        );
    }


    @PutMapping("/{requestId}/condominium/{condominiumId}/priority")
    public ResponseEntity<MaintenanceRequestResponse> changePriority(
            @PathVariable UUID requestId,
            @PathVariable Long condominiumId,
            @RequestParam Priority newPriority
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.changePriority(
                        requestId,
                        condominiumId,
                        newPriority
                )
        );
    }


    @PutMapping("/{requestId}/condominium/{condominiumId}/reject")
    public ResponseEntity<MaintenanceRequestResponse> reject(
            @PathVariable UUID requestId,
            @PathVariable Long condominiumId,
            @RequestParam String rejectionReason,
            @RequestParam UUID responsibleUserId
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.reject(
                        requestId,
                        condominiumId,
                        rejectionReason,
                        responsibleUserId
                )
        );
    }


    @PutMapping("/{requestId}/condominium/{condominiumId}/cancel")
    public ResponseEntity<MaintenanceRequestResponse> cancel(
            @PathVariable UUID requestId,
            @PathVariable Long condominiumId,
            @RequestParam UUID responsibleUserId
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.cancel(
                        requestId,
                        condominiumId,
                        responsibleUserId
                )
        );
    }


    @GetMapping("/request/{requestId}/condominium/{condominiumId}")
    public ResponseEntity<MaintenanceRequestResponse> findByIdAndCondominiumId(
            @PathVariable UUID requestId,
            @PathVariable Long condominiumId
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findByIdAndCondominiumId(
                        requestId,
                        condominiumId
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findAllByCondominiumId(
            @PathVariable Long condominiumId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findAllByCondominiumId(
                        condominiumId,
                        pageable
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}/status")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findByCondominiumIdAndStatus(
            @PathVariable Long condominiumId,
            @RequestParam MaintenanceRequestStatus status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findByCondominiumIdAndStatus(
                        condominiumId,
                        status,
                        pageable
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}/priority")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findByCondominiumIdAndPriority(
            @PathVariable Long condominiumId,
            @RequestParam Priority priority,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findByCondominiumIdAndPriority(
                        condominiumId,
                        priority,
                        pageable
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}/asset/{assetId}")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findByCondominiumIdAndAssetId(
            @PathVariable Long condominiumId,
            @PathVariable UUID assetId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findByCondominiumIdAndAssetId(
                        condominiumId,
                        assetId,
                        pageable
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}/category/{categoryId}")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findByCondominiumIdAndCategoryId(
            @PathVariable Long condominiumId,
            @PathVariable UUID categoryId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findByCondominiumIdAndCategoryId(
                        condominiumId,
                        categoryId,
                        pageable
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}/opened-at/between")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findByCondominiumIdAndOpenedAtBetween(
            @PathVariable Long condominiumId,
            @RequestParam Instant start,
            @RequestParam Instant end,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findByCondominiumIdAndOpenedAtBetween(
                        condominiumId,
                        start,
                        end,
                        pageable
                )
        );
    }


    @GetMapping("/condominium/{condominiumId}/overdue")
    public ResponseEntity<Page<MaintenanceRequestResponse>> findOverdueByCondominiumId(
            @PathVariable Long condominiumId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findOverdueByCondominiumId(
                        condominiumId,
                        pageable
                )
        );
    }

    @GetMapping("/{requestId}/condominium/{condominiumId}/history")
    public ResponseEntity<Page<MaintenanceRequestHistoryResponse>> findHistoryByRequestIdAndCondominiumId(
            @PathVariable UUID requestId,
            @PathVariable Long condominiumId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                maintenanceRequestService.findHistoryByRequestIdAndCondominiumId(
                        requestId,
                        condominiumId,
                        pageable
                )
        );
    }
}