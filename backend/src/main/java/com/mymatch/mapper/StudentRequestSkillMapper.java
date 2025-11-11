package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;
import com.mymatch.entity.StudentRequestSkill;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentRequestSkillMapper {
    @Mapping(target = "requestId", source = "request.id")
    StudentRequestSkillResponse toResponse(StudentRequestSkill entity);
}
