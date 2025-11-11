package com.mymatch.dto.response.dashboard;

import java.time.LocalDateTime;

import com.mymatch.enums.SwapStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapDashboardResponse {
    Long id;
    SwapStatus status;
    String studentFromName;
    String studentToName;
    Long campusId;
    String campusName;
    Long universityId;
    String universityName;
    LocalDateTime createAt;
    LocalDateTime matchedAt;
}
