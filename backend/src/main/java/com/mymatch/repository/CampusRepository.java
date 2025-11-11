package com.mymatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
    boolean existsByNameAndUniversityId(String name, Long universityId);

    Optional<Campus> findByIdAndUniversityId(Long campusId, Long universityId);
}
