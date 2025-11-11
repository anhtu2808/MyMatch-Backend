package com.mymatch.dto.request.member;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberCreationRequest {
    Long memberId;
    String name;
    String note;
    String image;
    Set<Long> skillIds;
}
