package com.mymatch.dto.response.dashboard;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDashboardResponse {
    Long id;
    String username;
    String email;
    String firstName;
    String lastName;
    String phone;
    String avatarUrl;
    String role;
    Boolean isActive;
    Long campusId;
    String campusName;
    Long universityId;
    String universityName;
    LocalDateTime createAt;
}
