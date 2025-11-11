package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.response.material.MaterialItemPreviewResponse;
import com.mymatch.dto.response.material.MaterialItemResponse;
import com.mymatch.entity.MaterialItem;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MaterialItemMapper {

    MaterialItemResponse toMaterialItemResponse(MaterialItem materialItem);

    MaterialItemPreviewResponse toMaterialItemPreviewResponse(MaterialItem materialItem);
}
