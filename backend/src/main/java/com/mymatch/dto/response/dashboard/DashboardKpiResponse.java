package com.mymatch.dto.response.dashboard;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardKpiResponse {
    Long totalUsers;
    Double revenue; // VND
    Long activeStudents;
    Long pendingActions;
    Long totalSwaps;
    Long totalTransactions;
}
