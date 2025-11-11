package com.mymatch.dto.response.dashboard;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGrowthData {
    LocalDateTime date;
    Long newUsers;
    Long totalUsers;
}
