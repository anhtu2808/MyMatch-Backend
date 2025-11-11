package com.mymatch.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mymatch.dto.request.chatmessage.ChatMessageCreationRequest;
import com.mymatch.dto.response.chatmessage.ChatMessageResponse;

public interface ChatMessageService {
    ChatMessageResponse createChatMessage(ChatMessageCreationRequest request) throws JsonProcessingException;

    List<ChatMessageResponse> getMessages(Long conversationId);
}
