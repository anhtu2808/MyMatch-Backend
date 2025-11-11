package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.response.member.MemberSkillResponse;
import com.mymatch.entity.MemberSkill;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberSkillMapper {
    MemberSkillResponse toResponse(MemberSkill memberSkill);
}
