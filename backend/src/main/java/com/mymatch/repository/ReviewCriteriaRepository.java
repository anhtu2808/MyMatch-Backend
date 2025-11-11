package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.ReviewCriteria;

@Repository
public interface ReviewCriteriaRepository
        extends JpaRepository<ReviewCriteria, Long>, JpaSpecificationExecutor<ReviewCriteria> {}
