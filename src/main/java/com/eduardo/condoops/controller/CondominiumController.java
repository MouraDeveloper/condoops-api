package com.eduardo.condoops.controller;

import com.eduardo.condoops.dto.condominium.CondominiumResponse;
import com.eduardo.condoops.dto.condominium.CreateCondominiumRequest;
import com.eduardo.condoops.dto.condominium.UpdateCondominiumRequest;
import com.eduardo.condoops.service.CondominiumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/condominiums")
@RequiredArgsConstructor
public class CondominiumController {

    private final CondominiumService condominiumService;


    @GetMapping("/{id}")
    public ResponseEntity<CondominiumResponse> findCondominiumById(@PathVariable Long id) {
        return ResponseEntity.ok(condominiumService.findCondominiumById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CondominiumResponse>> findAllCondominium(Pageable pageable) {
        return ResponseEntity.ok(condominiumService.findAllCondominiums(pageable));
    }

    @PostMapping
    public ResponseEntity<CondominiumResponse> createCondominium(
            @RequestBody @Valid CreateCondominiumRequest createCondominiumRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(condominiumService
                .createCondominium(createCondominiumRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondominiumById(@PathVariable Long id) {
        condominiumService.deleteCondominiumById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondominiumResponse> updateCondominiumById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCondominiumRequest updateCondominiumRequest
    ) {
        return ResponseEntity.ok(condominiumService.updateCondominium(id, updateCondominiumRequest));
    }
}
