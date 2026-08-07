package com.eduardo.condoops.service;

import com.eduardo.condoops.dto.category.CategoryResponse;
import com.eduardo.condoops.dto.category.CreateCategoryRequest;
import com.eduardo.condoops.dto.category.UpdateCategoryRequest;
import com.eduardo.condoops.entity.Category;
import com.eduardo.condoops.entity.Condominium;
import com.eduardo.condoops.exception.conflict.*;
import com.eduardo.condoops.exception.notfound.CategoryNotFoundException;
import com.eduardo.condoops.exception.notfound.CondominiumNotFoundException;
import com.eduardo.condoops.mapper.CategoryMapper;
import com.eduardo.condoops.repository.CategoryRepository;
import com.eduardo.condoops.repository.CondominiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CondominiumRepository condominiumRepository;


    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest category) {
        Condominium condominium = condominiumRepository.findById(category.condominiumId()).orElseThrow(
                () -> new CondominiumNotFoundException(
                        category
                                .condominiumId())
        );

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(category.condominiumId());
        }

        String name = category.name().strip();

        if (categoryRepository.existsByCondominiumIdAndNameIgnoreCase(category.condominiumId(), name)) {
            throw new CategoryNameAlreadyExistsException(
                    name,
                    category.condominiumId());
        }

        Category category1 = CategoryMapper.toEntity(category, name, condominium);

        categoryRepository.save(category1);

        return CategoryMapper.toResponse(category1);
    }


    @Transactional(readOnly = true)
    public CategoryResponse findById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException(id)
                );

        if (!category.isActive()) {
            throw new CategoryNotFoundException(id);
        }

        return CategoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(
            boolean active,
            Pageable pageable
    ) {
        return categoryRepository.findAllByActive(active, pageable)
                .map(CategoryMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findByCondominiumIdAndActive(
            Long id,
            boolean active,
            Pageable pageable
    ) {
        Condominium condominium = condominiumRepository.findById(id)
                .orElseThrow(
                        () -> new CondominiumNotFoundException(id)
                );

        if (!condominium.isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(id);
        }

        return categoryRepository.findByCondominiumIdAndActive(id, active, pageable)
                .map(CategoryMapper::toResponse);
    }

    @Transactional
    public CategoryResponse updateCategory(
            UUID id,
            UpdateCategoryRequest categoryRequest
    ) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException(id)
                );

        if (!category.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(category
                    .getCondominium()
                    .getId()
            );
        }

        if (!category.isActive()) {
            throw new InactiveCategoryOperationNotAllowedException(id);
        }

        String name = categoryRequest.name().strip();

        if (categoryRepository.existsByCondominiumIdAndNameIgnoreCaseAndIdNot(
                category.getCondominium().getId(),
                name,
                category.getId()
        )) {
            throw new CategoryNameAlreadyExistsException(name, category.getCondominium().getId());
        }

        category.update(
                name,
                categoryRequest.description(),
                categoryRequest.defaultResponseHours()
        );

        categoryRepository.flush();

        return CategoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse deactivateCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException(id)
                );

        if (!category.isActive()) {
            throw new CategoryAlreadyDeactivatedException(id);
        }

        category.deactivate();
        categoryRepository.flush();

        return CategoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse activateCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException(id)
                );

        if (!category.getCondominium().isActive()) {
            throw new InactiveCondominiumOperationNotAllowedException(
                    category.getCondominium().getId());
        }

        if (category.isActive()) {
            throw new CategoryAlreadyActivatedException(id);
        }

        category.activate();
        categoryRepository.flush();

        return CategoryMapper.toResponse(category);
    }

}
