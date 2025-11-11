package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.MemberSkill;

@Repository
public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {}
