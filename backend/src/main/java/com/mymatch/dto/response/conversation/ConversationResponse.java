package com.mymatch.dto.response.conversation;

import java.util.List;

import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.swap.SwapResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponse {
    Long id;
    String type; // GROUP, DIRECT
    String participantsHash;
    String conversationAvatar;
    String conversationName;
    List<StudentResponse> participants;
    boolean me;
    SwapResponse swap;
}
