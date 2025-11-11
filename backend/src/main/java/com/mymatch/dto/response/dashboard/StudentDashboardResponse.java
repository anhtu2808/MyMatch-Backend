package com.mymatch.dto.response.dashboard;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDashboardResponse {
    Long id;
    String studentCode;
    String firstName;
    String lastName;
    String email;
    Boolean isActive;
    Long campusId;
    String campusName;
    Long universityId;
    String universityName;
    String major;
    LocalDateTime createAt;
}
