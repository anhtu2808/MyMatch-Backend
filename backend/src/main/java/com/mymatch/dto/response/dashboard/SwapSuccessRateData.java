package com.mymatch.dto.response.dashboard;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapSuccessRateData {
    Long totalSwaps;
    Long approvedSwaps;
    Long rejectedSwaps;
    Long pendingSwaps;
    Double successRate; // percentage
}
