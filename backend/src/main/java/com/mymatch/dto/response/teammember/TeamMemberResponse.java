package com.mymatch.dto.response.teammember;

import java.time.LocalDateTime;

import com.mymatch.dto.response.member.MemberResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamMemberResponse {
    Long id;
    MemberResponse member;
    LocalDateTime createAt;
}
