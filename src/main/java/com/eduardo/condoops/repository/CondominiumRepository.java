package com.eduardo.condoops.repository;

import com.eduardo.condoops.entity.Condominium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondominiumRepository extends JpaRepository<Condominium, Long> {

    boolean existsByDocument(String document);

    boolean existsByDocumentAndIdNot(String document, Long id);

}
