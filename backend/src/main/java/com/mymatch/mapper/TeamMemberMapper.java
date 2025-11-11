package com.mymatch.mapper;

import org.mapstruct.*;

import com.mymatch.dto.response.teammember.TeamMemberResponse;
import com.mymatch.entity.TeamMember;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMemberMapper {
    TeamMemberResponse toResponse(TeamMember entity);
}
