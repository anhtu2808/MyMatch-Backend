package com.mymatch.dto.response.member;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberResponse {
    Long id;
    String name;
    String note;
    String image;
    Set<MemberSkillResponse> memberSkills;
}
