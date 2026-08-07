package com.eduardo.condoops.mapper;

import com.eduardo.condoops.dto.category.CategoryResponse;
import com.eduardo.condoops.dto.category.CreateCategoryRequest;
import com.eduardo.condoops.entity.Category;
import com.eduardo.condoops.entity.Condominium;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toEntity(CreateCategoryRequest request, String name, Condominium condominium) {
        return new Category(
                name,
                request.description(),
                condominium,
                request.defaultResponseHours()
        );
    }

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getDefaultResponseHours(),
                category.isActive(),
                category.getCondominium().getId(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

}
