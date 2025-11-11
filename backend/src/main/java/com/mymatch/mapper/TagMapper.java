package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.response.tag.TagResponse;
import com.mymatch.entity.Tag;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {

    TagResponse toTagResponse(Tag tag);
}
