package com.mymatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mymatch.entity.Course;
import com.mymatch.entity.University;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    boolean existsByCodeAndUniversity(String code, University university);

    Optional<Course> findByName(String name);

    Optional<Course> findByCode(String code);
}
