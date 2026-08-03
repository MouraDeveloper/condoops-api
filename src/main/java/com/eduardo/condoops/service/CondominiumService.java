package com.eduardo.condoops.service;

import com.eduardo.condoops.dto.condominium.CondominiumResponse;
import com.eduardo.condoops.dto.condominium.CreateCondominiumRequest;
import com.eduardo.condoops.dto.condominium.UpdateCondominiumRequest;
import com.eduardo.condoops.entity.Condominium;
import com.eduardo.condoops.exception.conflict.CondominiumDocumentAlreadyExistsException;
import com.eduardo.condoops.exception.notfound.CondominiumNotFoundException;
import com.eduardo.condoops.mapper.CondominiumMapper;
import com.eduardo.condoops.repository.CondominiumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CondominiumService {

    private final CondominiumRepository condominiumRepository;


    public CondominiumResponse findCondominiumById(Long id) {
        return condominiumRepository.findById(id)
                .map(CondominiumMapper::toResponse)
                .orElseThrow(() -> new CondominiumNotFoundException(id));
    }

    public Page<CondominiumResponse> findAllCondominiums(Pageable pageable) {
        return condominiumRepository.findAll(pageable)
                .map(CondominiumMapper::toResponse);
    }

    @Transactional
    public void deleteCondominiumById(Long id) {
        Condominium condominium = condominiumRepository.findById(id)
                .orElseThrow(() -> new CondominiumNotFoundException(id));

        condominiumRepository.delete(condominium);
    }

    @Transactional
    public CondominiumResponse updateCondominium(
            Long id,
            UpdateCondominiumRequest condominiumRequest) {

        Condominium condominium = condominiumRepository.findById(id)
                .orElseThrow(() -> new CondominiumNotFoundException(id));

        if (condominiumRepository.existsByDocumentAndIdNot(condominiumRequest.document(), id)) {
            throw new CondominiumDocumentAlreadyExistsException(condominiumRequest.document());
        }

        condominium.updateData(
                condominiumRequest.name(),
                condominiumRequest.document()
        );

        condominiumRepository.flush();

        return CondominiumMapper.toResponse(condominium);
    }

    @Transactional
    public CondominiumResponse createCondominium(CreateCondominiumRequest createCondominiumRequest) {

        if (condominiumRepository.existsByDocument(createCondominiumRequest.document())) {
            throw new CondominiumDocumentAlreadyExistsException(
                    createCondominiumRequest.document()
            );
        }

        Condominium condominium = CondominiumMapper.toEntity(createCondominiumRequest);

        Condominium savedCondominium = condominiumRepository.save(condominium);

        return CondominiumMapper.toResponse(savedCondominium);
    }
}
