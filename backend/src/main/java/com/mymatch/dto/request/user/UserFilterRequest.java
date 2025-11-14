package com.mymatch.dto.request.user;

import com.mymatch.enums.RoleType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilterRequest {
    @Builder.Default
    int page = 1;

    @Builder.Default
    int size = 10;

    String sort;
    RoleType role;
    Integer deleted;
    Boolean isActive;
    String username;
    String email;
    String search;
    Long universityId;
    Long campusId;
}
