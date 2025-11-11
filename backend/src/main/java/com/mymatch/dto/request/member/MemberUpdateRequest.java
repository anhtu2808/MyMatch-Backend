package com.mymatch.dto.request.member;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberUpdateRequest {
    Long id;
    String name;
    String note;
    String image;
    List<Long> skillIds;
}
