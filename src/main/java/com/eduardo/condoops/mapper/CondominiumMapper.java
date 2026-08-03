package com.eduardo.condoops.mapper;

import com.eduardo.condoops.dto.condominium.CondominiumResponse;
import com.eduardo.condoops.dto.condominium.CreateCondominiumRequest;
import com.eduardo.condoops.entity.Condominium;

public final class CondominiumMapper {

    private CondominiumMapper() {
    }

    public static Condominium toEntity(CreateCondominiumRequest request) {
        return Condominium.builder()
                .name(request.name())
                .document(request.document())
                .build();
    }

    public static CondominiumResponse toResponse(Condominium condominium) {
        return new CondominiumResponse(
                condominium.getId(),
                condominium.getName(),
                condominium.getDocument(),
                condominium.isActive(),
                condominium.getCreatedAt(),
                condominium.getUpdatedAt()
        );
    }
}