package com.mymatch.service;

import java.util.List;

import com.mymatch.dto.request.conversation.ConversationCreationRequest;
import com.mymatch.dto.response.conversation.ConversationResponse;

public interface ConversationService {
    List<ConversationResponse> myConversations();

    ConversationResponse createConversation(ConversationCreationRequest request);
}
