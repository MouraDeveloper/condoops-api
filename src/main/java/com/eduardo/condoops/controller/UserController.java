package com.eduardo.condoops.controller;

import com.eduardo.condoops.dto.user.ChangeUserRoleRequest;
import com.eduardo.condoops.dto.user.CreateUserRequest;
import com.eduardo.condoops.dto.user.UpdateUserRequest;
import com.eduardo.condoops.dto.user.UserResponse;
import com.eduardo.condoops.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService
                .findById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<UserResponse>> findAllActiveUsers(Pageable pageable) {
        return ResponseEntity.ok(userService
                .findAllActiveUsers(pageable));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<UserResponse>> findAllInactiveUsers(Pageable pageable) {
        return ResponseEntity.ok(userService
                .findAllInactiveUsers(pageable));
    }

    @GetMapping("/condominium/{condominiumId}")
    public ResponseEntity<Page<UserResponse>> findAllByCondominiumId(
            Pageable pageable,
            @PathVariable Long condominiumId) {
        return ResponseEntity.ok(userService
                .findAllUsersByCondominiumId(pageable, condominiumId));
    }
    

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserRequest userRequest
    ) {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }


    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivateUser(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.activateUser(id));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable UUID id,
            @RequestBody @Valid ChangeUserRoleRequest userRoleRequest
    ) {
        return ResponseEntity.ok(userService.updateRole(id, userRoleRequest));
    }
}
