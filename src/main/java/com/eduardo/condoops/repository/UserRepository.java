package com.eduardo.condoops.repository;

import com.eduardo.condoops.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

    Page<User> findByCondominiumId(
            Long condominiumId,
            Pageable pageable
    );
}
