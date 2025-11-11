package com.mymatch.dto.request.dashboard;

import com.mymatch.enums.SwapStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapTableFilterRequest extends DashboardFilterRequest {
    SwapStatus status;
}
