package com.mymatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.TeamRequestSkill;

@Repository
public interface TeamRequestSkillRepository extends JpaRepository<TeamRequestSkill, Long> {}
