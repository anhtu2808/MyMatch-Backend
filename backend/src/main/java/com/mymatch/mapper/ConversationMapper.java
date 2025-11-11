package com.mymatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mymatch.dto.response.conversation.ConversationResponse;
import com.mymatch.entity.Conversation;

@Mapper(
        componentModel = "spring",
        uses = {StudentMapper.class},
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {

    @Mapping(target = "participants", ignore = true)
    ConversationResponse toConversationResponse(Conversation conversation);
}
