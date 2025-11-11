package com.mymatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.Role;
import com.mymatch.enums.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType type);
}
