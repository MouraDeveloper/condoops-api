package com.eduardo.condoops.service;

import com.eduardo.condoops.dto.maintenanceRequest.CreateMaintenanceRequestDto;
import com.eduardo.condoops.dto.maintenanceRequest.MaintenanceRequestResponse;
import com.eduardo.condoops.entity.*;
import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import com.eduardo.condoops.entity.enums.Priority;
import com.eduardo.condoops.exception.conflict.CrossCondominiumResourceException;
import com.eduardo.condoops.exception.conflict.InactiveMaintenanceRequestResourceException;
import com.eduardo.condoops.exception.conflict.InvalidMaintenanceRequestStatusException;
import com.eduardo.condoops.exception.conflict.MissingRejectionReasonException;
import com.eduardo.condoops.exception.notfound.*;
import com.eduardo.condoops.mapper.MaintenanceRequestMapper;
import com.eduardo.condoops.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final AssetRepository assetRepository;
    private final CondominiumRepository condominiumRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Transactional
    public MaintenanceRequestResponse createMaintenance(
            CreateMaintenanceRequestDto requestDto,
            Long condominiumId
    ) {
        MaintenanceRequest maintenanceRequest =
                buildMaintenanceRequest(requestDto, condominiumId);

        MaintenanceRequest saved =
                maintenanceRequestRepository.save(maintenanceRequest);

        return MaintenanceRequestMapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public MaintenanceRequestResponse findByIdAndCondominiumId(
            UUID requestId,
            Long condominiumId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        return MaintenanceRequestMapper.toResponse(request);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findAllByCondominiumId(
            Long condominiumId,
            Pageable pageable
    ) {
        return maintenanceRequestRepository
                .findByCondominiumId(condominiumId, pageable)
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findByCondominiumIdAndStatus(
            Long condominiumId,
            MaintenanceRequestStatus status,
            Pageable pageable
    ) {
        return maintenanceRequestRepository
                .findByCondominiumIdAndStatus(condominiumId, status, pageable)
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findByCondominiumIdAndPriority(
            Long condominiumId,
            Priority priority,
            Pageable pageable
    ) {
        return maintenanceRequestRepository
                .findByCondominiumIdAndPriority(condominiumId, priority, pageable)
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findByCondominiumIdAndAssetId(
            Long condominiumId,
            UUID assetId,
            Pageable pageable
    ) {
        return maintenanceRequestRepository
                .findByCondominiumIdAndAssetId(condominiumId, assetId, pageable)
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findByCondominiumIdAndCategoryId(
            Long condominiumId,
            UUID categoryId,
            Pageable pageable
    ) {
        return maintenanceRequestRepository
                .findByCondominiumIdAndCategoryId(condominiumId, categoryId, pageable)
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findByCondominiumIdAndOpenedAtBetween(
            Long condominiumId,
            Instant start,
            Instant end,
            Pageable pageable
    ) {
        return maintenanceRequestRepository
                .findByCondominiumIdAndOpenedAtBetween(
                        condominiumId,
                        start,
                        end,
                        pageable
                )
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findOverdueByCondominiumId(
            Long condominiumId,
            Pageable pageable
    ) {
        Instant now = Instant.now();

        List<MaintenanceRequestStatus> terminalStatuses = List.of(
                MaintenanceRequestStatus.COMPLETED,
                MaintenanceRequestStatus.CANCELED,
                MaintenanceRequestStatus.REJECTED
        );

        return maintenanceRequestRepository
                .findByCondominiumIdAndDeadlineBeforeAndStatusNotIn(
                        condominiumId,
                        now,
                        terminalStatuses,
                        pageable
                )
                .map(MaintenanceRequestMapper::toResponse);
    }


    @Transactional
    public MaintenanceRequestResponse sendToReview(
            UUID requestId,
            Long condominiumId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (!request.getStatus().equals(MaintenanceRequestStatus.OPEN)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        request.sendToReview();

        return MaintenanceRequestMapper.toResponse(request);
    }


    @Transactional
    public MaintenanceRequestResponse changePriority(
            UUID requestId,
            Long condominiumId,
            Priority newPriority
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (request.getStatus().equals(MaintenanceRequestStatus.COMPLETED)
                || request.getStatus().equals(MaintenanceRequestStatus.CANCELED)
                || request.getStatus().equals(MaintenanceRequestStatus.REJECTED)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        request.changePriority(newPriority);

        return MaintenanceRequestMapper.toResponse(request);
    }


    @Transactional
    public MaintenanceRequestResponse reject(
            UUID requestId,
            Long condominiumId,
            String rejectionReason
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (!request.getStatus().equals(MaintenanceRequestStatus.UNDER_REVIEW)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        if (rejectionReason == null || rejectionReason.isBlank()) {
            throw new MissingRejectionReasonException();
        }

        request.reject(rejectionReason);

        return MaintenanceRequestMapper.toResponse(request);
    }


    @Transactional
    public MaintenanceRequestResponse cancel(
            UUID requestId,
            Long condominiumId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (!request.getStatus().equals(MaintenanceRequestStatus.OPEN)
                && !request.getStatus().equals(MaintenanceRequestStatus.UNDER_REVIEW)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        request.cancel();

        return MaintenanceRequestMapper.toResponse(request);
    }


    private MaintenanceRequest buildMaintenanceRequest(
            CreateMaintenanceRequestDto requestDto,
            Long condominiumId
    ) {
        Asset asset = assetRepository.findById(requestDto.assetId())
                .orElseThrow(
                        () -> new AssetNotFoundException(requestDto.assetId())
                );

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(
                        () -> new CategoryNotFoundException(requestDto.categoryId())
                );

        User user = userRepository.findById(requestDto.requesterId())
                .orElseThrow(
                        () -> new UserNotFoundException(requestDto.requesterId())
                );

        Condominium condominium = condominiumRepository.findById(condominiumId)
                .orElseThrow(
                        () -> new CondominiumNotFoundException(condominiumId)
                );

        if (!asset.isActive()
                || !user.isActive()
                || !category.isActive()
                || !condominium.isActive()) {
            throw new InactiveMaintenanceRequestResourceException();
        }

        if (user.getCondominium() == null
                || !asset.getCondominium().getId().equals(condominiumId)
                || !category.getCondominium().getId().equals(condominiumId)
                || !user.getCondominium().getId().equals(condominiumId)) {
            throw new CrossCondominiumResourceException();
        }

        return MaintenanceRequestMapper.toEntity(
                requestDto,
                asset,
                category,
                user,
                condominium
        );
    }


    private MaintenanceRequest findEntityByIdAndCondominiumId(
            UUID requestId,
            Long condominiumId
    ) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new MaintenanceRequestNotFound(requestId));

        if (!request.getCondominium().getId().equals(condominiumId)) {
            throw new MaintenanceRequestNotFound(requestId);
        }

        return request;
    }
}