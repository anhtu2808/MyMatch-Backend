package com.mymatch.dto.response.teamrequest;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestResponse {
    Long id;
    String title;
    Set<TeamRequestSkillResponse> skills;
}
