package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {}
