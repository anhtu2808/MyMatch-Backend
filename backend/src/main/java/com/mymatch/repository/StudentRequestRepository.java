package com.mymatch.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.StudentRequest;

@Repository
public interface StudentRequestRepository
        extends JpaRepository<StudentRequest, Long>, JpaSpecificationExecutor<StudentRequest> {

    // Bulk UPDATE để đánh dấu EXPIRED cho các request đã hết hạn
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
            """
		UPDATE StudentRequest sr
		SET sr.status = com.mymatch.enums.RequestStatus.EXPIRED
		WHERE sr.deleted = 0
		AND sr.status = com.mymatch.enums.RequestStatus.OPEN
		AND sr.expiresAt IS NOT NULL
		AND sr.expiresAt < :now
	""")
    int expireOpenRequests(@Param("now") LocalDateTime now);
}
