package com.mymatch.dto.response.dashboard;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterOptionsResponse {
    List<UniversityOption> universities;
    List<CampusOption> campuses;
}
