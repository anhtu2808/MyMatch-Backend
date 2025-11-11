package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.request.plan.PlanCreationRequest;
import com.mymatch.dto.request.plan.PlanUpdateRequest;
import com.mymatch.dto.response.plan.PlanResponse;
import com.mymatch.entity.Plan;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlanMapper {

    Plan toEntity(PlanCreationRequest req);

    PlanResponse toResponse(Plan entity);

    void updateEntity(@MappingTarget Plan entity, PlanUpdateRequest req);
}
