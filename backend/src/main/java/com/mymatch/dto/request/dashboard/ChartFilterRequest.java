package com.mymatch.dto.request.dashboard;

import com.mymatch.enums.DateGroupBy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChartFilterRequest extends DashboardFilterRequest {
    DateGroupBy groupBy;
}
