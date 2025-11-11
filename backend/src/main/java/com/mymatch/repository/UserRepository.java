package com.mymatch.repository;

import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(@Email @NotBlank String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(@Email @NotBlank String email);
}
