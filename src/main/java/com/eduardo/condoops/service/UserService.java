package com.eduardo.condoops.service;

import com.eduardo.condoops.dto.user.ChangeUserRoleRequest;
import com.eduardo.condoops.dto.user.CreateUserRequest;
import com.eduardo.condoops.dto.user.UpdateUserRequest;
import com.eduardo.condoops.dto.user.UserResponse;
import com.eduardo.condoops.entity.Condominium;
import com.eduardo.condoops.entity.User;
import com.eduardo.condoops.exception.conflict.*;
import com.eduardo.condoops.exception.notfound.CondominiumNotFoundException;
import com.eduardo.condoops.exception.notfound.UserNotFoundException;
import com.eduardo.condoops.mapper.UserMapper;
import com.eduardo.condoops.repository.CondominiumRepository;
import com.eduardo.condoops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CondominiumRepository condominiumRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public UserResponse findById(UUID id) {
        return userRepository.findById(id)
                .filter(User::isActive)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAllActiveUsers(Pageable pageable) {
        return userRepository.findByActive(true, pageable)
                .map(UserMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAllInactiveUsers(Pageable pageable) {
        return userRepository.findByActive(false, pageable)
                .map(UserMapper::toResponse);
    }


    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        String email = request.email().toLowerCase(Locale.ROOT).strip();

        if (userRepository.existsByEmail(email)) {
            throw new UserEmailAlreadyExistsException(email);
        }

        Condominium condominium = condominiumRepository.findById(request.condominiumId())
                .orElseThrow(
                        () -> new CondominiumNotFoundException(request.condominiumId())
                );

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(request.condominiumId());
        }

        User user = UserMapper.toEntity(request, email, condominium);

        User userSaved = userRepository.save(user);

        return UserMapper.toResponse(userSaved);
    }


    @Transactional
    public UserResponse updateUser(
            UUID id,
            UpdateUserRequest updateUserRequest
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.isActive()) {
            throw new InactiveUserOperationNotAllowedException(id);
        }

        if (!user.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(user.getCondominium().getId());
        }

        String email = updateUserRequest.email().toLowerCase(Locale.ROOT).strip();

        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new UserEmailAlreadyExistsException(email);
        }

        user.updateData(
                updateUserRequest.name(),
                email
        );

        userRepository.flush();

        return UserMapper.toResponse(user);
    }


    @Transactional
    public UserResponse deactivateUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(id)
                );

        if (!user.isActive()) {
            throw new UserAlreadyDeactivatedException(id);
        }

        user.deactivate();

        userRepository.flush();

        return UserMapper.toResponse(user);
    }


    @Transactional
    public UserResponse activateUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(id)
                );
        if (user.isActive()) {
            throw new UserAlreadyActivatedException(id);
        }

        if (!user.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(user.getCondominium().getId());
        }

        user.activate();

        userRepository.flush();

        return UserMapper.toResponse(user);
    }


    @Transactional
    public UserResponse updateRole(UUID id, ChangeUserRoleRequest roleRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(id)
                );

        if (!user.isActive()) {
            throw new InactiveUserOperationNotAllowedException(id);
        }

        if (!user.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(user.getCondominium().getId());
        }

        if (roleRequest.role() == user.getRole()) {
            return UserMapper.toResponse(user);
        }

        user.changeRole(roleRequest.role());

        userRepository.flush();

        return UserMapper.toResponse(user);
    }


    @Transactional(readOnly = true)
    public Page<UserResponse> findAllUsersByCondominiumId(
            Pageable pageable,
            Long id
    ) {
        Condominium condominium = condominiumRepository.findById(id)
                .orElseThrow(
                        () -> new CondominiumNotFoundException(id)
                );

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(id);
        }

        Page<User> users = userRepository.findByCondominiumId(
                condominium.getId(),
                pageable
        );

        return users.map(UserMapper::toResponse);
    }
}