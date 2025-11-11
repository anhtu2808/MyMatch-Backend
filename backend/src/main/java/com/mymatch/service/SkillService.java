package com.mymatch.service;

import java.util.List;

import com.mymatch.dto.response.skill.SkillResponse;

public interface SkillService {
    List<SkillResponse> getAllSkills();
}
