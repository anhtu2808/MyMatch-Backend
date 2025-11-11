package com.mymatch.dto.request.teamrequest;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestUpdateRequest {
    @NotNull
    Long id;

    @NotBlank
    String title;

    @Size(max = 1000)
    String description;

    Set<Long> skillIds; // full replace
}
