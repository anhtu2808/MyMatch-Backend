package com.mymatch.mapper;

import org.mapstruct.*;

import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaCreateRequest;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaUpdateRequest;
import com.mymatch.dto.response.reviewcriteria.ReviewCriteriaResponse;
import com.mymatch.entity.ReviewCriteria;

@Mapper(componentModel = "spring")
public interface ReviewCriteriaMapper {

    @Mapping(target = "id", ignore = true)
    ReviewCriteria toEntity(ReviewCriteriaCreateRequest request);

    ReviewCriteriaResponse toResponse(ReviewCriteria entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget ReviewCriteria entity, ReviewCriteriaUpdateRequest request);
}
