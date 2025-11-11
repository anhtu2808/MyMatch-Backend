package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.TeamMember;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    boolean existsByTeam_IdAndMember_Id(Long teamId, Long memberId);

    boolean existsByMember_Id(Long memberId);
}
