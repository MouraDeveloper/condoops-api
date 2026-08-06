package com.eduardo.condoops.controller;

import com.eduardo.condoops.dto.category.CategoryResponse;
import com.eduardo.condoops.dto.category.CreateCategoryRequest;
import com.eduardo.condoops.dto.category.UpdateCategoryRequest;
import com.eduardo.condoops.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody @Valid CreateCategoryRequest categoryRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryRequest));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAll(
            @RequestParam(defaultValue = "true") boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.findAll(active, pageable));
    }

    @GetMapping("/condominium/{condominiumId}")
    public ResponseEntity<Page<CategoryResponse>> findByCondominiumIdAndActive(
            @PathVariable Long condominiumId,
            @RequestParam(defaultValue = "true") boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.findByCondominiumIdAndActive(
                condominiumId,
                active,
                pageable
        ));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateCategoryRequest categoryRequest
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(
                id,
                categoryRequest
        ));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CategoryResponse> activateCategory(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(categoryService.activateCategory(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CategoryResponse> deactivateCategory(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(categoryService.deactivateCategory(id));
    }
}
