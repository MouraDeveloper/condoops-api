package com.eduardo.condoops.repository;

import com.eduardo.condoops.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByCondominiumIdAndName(Long id, String name);

    boolean existsByNameAndCondominiumIdAndIdNot(String name, Long condominiumId, UUID id);

    Page<Category> findAllByActive(boolean active, Pageable pageable);

    Page<Category> findByCondominiumIdAndActive(Long condominiumId, boolean active, Pageable pageable);
}
