package com.eduardo.condoops.mapper;

import com.eduardo.condoops.dto.user.CreateUserRequest;
import com.eduardo.condoops.dto.user.UserResponse;
import com.eduardo.condoops.entity.Condominium;
import com.eduardo.condoops.entity.User;

public final class UserMapper {

    private UserMapper() {
    }


    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCondominium().getId(),
                user.getCondominium().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static User toEntity(
            CreateUserRequest request,
            Condominium condominium
    ) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .role(request.role())
                .condominium(condominium)
                .build();
    }

}
