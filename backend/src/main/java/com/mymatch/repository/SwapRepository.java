package com.mymatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mymatch.entity.Swap;

public interface SwapRepository extends JpaRepository<Swap, Long>, JpaSpecificationExecutor<Swap> {
    Optional<Swap> findByIdAndStudentFromIdOrStudentToId(Long id, Long studentFromId, Long studentToId);

    @Query(
            """
	select (count(s)>0) from Swap s
	where (s.requestFrom.id = :swapRequestCurrent and s.requestTo.id = :existingSwapRequest)
		or (s.requestFrom.id = :existingSwapRequest and s.requestTo.id = :swapRequestCurrent)
		and s.deleted = 0
	""")
    boolean existsByPairEitherOrder(
            @Param("swapRequestCurrent") Long swapRequestCurrent,
            @Param("existingSwapRequest") Long existingSwapRequest);
}
