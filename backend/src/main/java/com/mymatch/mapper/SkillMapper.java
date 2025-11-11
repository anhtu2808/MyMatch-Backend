package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.response.skill.SkillResponse;
import com.mymatch.entity.Skill;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkillMapper {
    SkillResponse toResponse(Skill skill);
}
