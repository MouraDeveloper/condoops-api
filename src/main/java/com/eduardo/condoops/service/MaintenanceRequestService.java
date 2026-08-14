package com.eduardo.condoops.service;

import com.eduardo.condoops.dto.maintenanceRequest.CreateMaintenanceRequestDto;
import com.eduardo.condoops.dto.maintenanceRequest.MaintenanceRequestResponse;
import com.eduardo.condoops.dto.maintenanceRequestHistory.MaintenanceRequestHistoryResponse;
import com.eduardo.condoops.entity.*;
import com.eduardo.condoops.entity.MaintenanceRequestHistory;
import com.eduardo.condoops.entity.enums.MaintenanceRequestStatus;
import com.eduardo.condoops.entity.enums.Priority;
import com.eduardo.condoops.exception.conflict.*;
import com.eduardo.condoops.exception.notfound.*;
import com.eduardo.condoops.mapper.MaintenanceRequestHistoryMapper;
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
    private final MaintenanceRequestHistoryRepository maintenanceRequestHistoryRepository;
    private final WorkOrderRepository workOrderRepository;


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
            Long condominiumId,
            UUID responsibleUserId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (!request.getStatus().equals(MaintenanceRequestStatus.OPEN)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        MaintenanceRequestStatus statusAntigo = request.getStatus();

        User user = findUserByIdAndCondominiumId(responsibleUserId, condominiumId);

        request.sendToReview();

        MaintenanceRequestHistory history = new MaintenanceRequestHistory(
                request,
                statusAntigo,
                request.getStatus(),
                "Maintenance request sent for review.",
                user
        );

        maintenanceRequestHistoryRepository.save(history);

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
            String rejectionReason,
            UUID responsibleUserId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (!request.getStatus().equals(MaintenanceRequestStatus.UNDER_REVIEW)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        if (rejectionReason == null || rejectionReason.isBlank()) {
            throw new MissingRejectionReasonException();
        }

        MaintenanceRequestStatus status = request.getStatus();

        User user = findUserByIdAndCondominiumId(responsibleUserId, condominiumId);

        request.reject(rejectionReason);

        MaintenanceRequestHistory requestHistory = new MaintenanceRequestHistory(
                request,
                status,
                request.getStatus(),
                rejectionReason,
                user
        );

        maintenanceRequestHistoryRepository.save(requestHistory);

        return MaintenanceRequestMapper.toResponse(request);
    }


    @Transactional
    public MaintenanceRequestResponse cancel(
            UUID requestId,
            Long condominiumId,
            UUID responsibleUserId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (!request.getStatus().equals(MaintenanceRequestStatus.OPEN)
                && !request.getStatus().equals(MaintenanceRequestStatus.UNDER_REVIEW)) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        MaintenanceRequestStatus status = request.getStatus();

        User user = findUserByIdAndCondominiumId(responsibleUserId, condominiumId);

        request.cancel();

        MaintenanceRequestHistory requestHistory = new MaintenanceRequestHistory(
                request,
                status,
                request.getStatus(),
                "Maintenance request canceled.",
                user
        );


        maintenanceRequestHistoryRepository.save(requestHistory);

        return MaintenanceRequestMapper.toResponse(request);
    }

    @Transactional
    public MaintenanceRequestResponse approve(
            UUID requestId,
            Long condominiumId,
            UUID responsibleUserId
    ) {
        MaintenanceRequest request =
                findEntityByIdAndCondominiumId(requestId, condominiumId);

        if (request.getStatus() != MaintenanceRequestStatus.UNDER_REVIEW) {
            throw new InvalidMaintenanceRequestStatusException();
        }

        User user =
                findUserByIdAndCondominiumId(responsibleUserId, condominiumId);

        if (workOrderRepository.existsByMaintenanceRequest_Id(requestId)) {
            throw new WorkOrderAlreadyExistsException();
        }

        MaintenanceRequestStatus previousStatus = request.getStatus();

        request.approve();

        String orderNumber =
                "WO-" + UUID.randomUUID().toString().toUpperCase();

        WorkOrder workOrder = new WorkOrder(
                orderNumber,
                request,
                request.getCondominium()
        );

        workOrderRepository.save(workOrder);

        MaintenanceRequestHistory history = new MaintenanceRequestHistory(
                request,
                previousStatus,
                request.getStatus(),
                "Maintenance request approved.",
                user
        );

        maintenanceRequestHistoryRepository.save(history);

        return MaintenanceRequestMapper.toResponse(request);
    }

    @Transactional(readOnly = true)
    public Page<MaintenanceRequestHistoryResponse> findHistoryByRequestIdAndCondominiumId(
            UUID requestId,
            Long condominiumId,
            Pageable pageable
    ) {

        MaintenanceRequest request = findEntityByIdAndCondominiumId(requestId, condominiumId);

        Page<MaintenanceRequestHistory> requestHistory = maintenanceRequestHistoryRepository
                .findByMaintenanceRequest_IdOrderByChangedAtAsc(request.getId(), pageable);

        return requestHistory.map(
                MaintenanceRequestHistoryMapper::toResponse
        );
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

    private User findUserByIdAndCondominiumId(
            UUID userId,
            Long condominiumId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getCondominium() == null
                || !user.getCondominium().getId().equals(condominiumId)) {
            throw new CrossCondominiumResourceException();
        }

        if (!user.getCondominium().isActive()) {
            throw new InactiveMaintenanceRequestResourceException();
        }

        if (!user.isActive()) {
            throw new InactiveMaintenanceRequestResourceException();
        }

        return user;
    }
}