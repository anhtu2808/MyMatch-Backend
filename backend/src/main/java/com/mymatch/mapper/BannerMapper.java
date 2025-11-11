package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.request.banner.BannerCreateRequest;
import com.mymatch.dto.request.banner.BannerUpdateRequest;
import com.mymatch.dto.response.banner.BannerResponse;
import com.mymatch.entity.Banner;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BannerMapper {

    Banner toEntity(BannerCreateRequest req);

    BannerResponse toResponse(Banner entity);

    void updateEntity(@MappingTarget Banner entity, BannerUpdateRequest req);
}
