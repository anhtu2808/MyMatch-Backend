package com.mymatch.repository;

import com.mymatch.entity.SwapRequest;

import com.mymatch.enums.ClassesSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long>, JpaSpecificationExecutor<SwapRequest> {
    Optional<SwapRequest> findByIdAndStudentId(Long id, Long studentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT sr FROM SwapRequest sr
        WHERE sr.course.id = :courseId
          AND sr.fromClass = :fromClass
          AND sr.targetClass = :targetClass
          AND sr.lecturerFrom.id = :lecturerFromId
          AND sr.lecturerTo.id = :lecturerToId
          AND sr.slotFrom = :slotFrom
          AND sr.slotTo = :slotTo
          AND sr.student.id <> :studentId
          AND sr.status = 'SENT'
          AND sr.expiresAt > CURRENT_TIMESTAMP
        ORDER BY sr.createAt ASC
    """)
Optional<SwapRequest> findMatchingPartnerRequests(
        @Param("courseId") Long courseId,
        @Param("fromClass") String fromClass,
        @Param("targetClass") String targetClass,
        @Param("lecturerFromId") Long lecturerFromId,
        @Param("lecturerToId") Long lecturerToId,
        @Param("slotFrom") ClassesSlot slotFrom,
        @Param("slotTo") ClassesSlot slotTo,
        @Param("studentId") Long studentId
);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT sr FROM SwapRequest sr
            WHERE sr.course.id = :courseId
              AND sr.fromClass = :fromClass
              AND sr.targetClass = :targetClass
              AND sr.lecturerFrom.id = :lecturerFromId
              AND sr.lecturerTo.id = :lecturerToId
              AND sr.slotFrom = :slotFrom
              AND sr.slotTo = :slotTo
              AND sr.student.id = :studentId
              AND sr.status = 'SENT'
            """)
    Optional<SwapRequest> findExistingRequestByStudent(
            @Param("courseId") Long courseId,
            @Param("fromClass") String fromClass,
            @Param("targetClass") String targetClass,
            @Param("lecturerFromId") Long lecturerFromId,
            @Param("lecturerToId") Long lecturerToId,
            @Param("slotFrom") ClassesSlot slotFrom,
            @Param("slotTo") ClassesSlot slotTo,
            @Param("studentId") Long studentId
    );

    // Đổi trạng thái các request đã quá hạn: chỉ đổi từ SENT → EXPIRED
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE SwapRequest sr
           SET sr.status = com.mymatch.enums.SwapRequestStatus.EXPIRED
         WHERE sr.deleted = 0
           AND sr.status = com.mymatch.enums.SwapRequestStatus.SENT
           AND sr.expiresAt < :now
    """)
    int expireSentRequests(@Param("now") LocalDateTime now);
}
