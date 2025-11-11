package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.ReviewDetail;

@Repository
public interface ReviewDetailRepository
        extends JpaRepository<ReviewDetail, Long>, JpaSpecificationExecutor<ReviewDetail> {}
