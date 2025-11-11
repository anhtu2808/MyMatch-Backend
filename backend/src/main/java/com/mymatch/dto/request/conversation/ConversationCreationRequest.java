package com.mymatch.dto.request.conversation;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.mymatch.enums.ConversationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationCreationRequest {
    ConversationType type;

    @Size(min = 1)
    @NotNull
    List<Long> participantIds;
}
