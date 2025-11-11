package com.mymatch.dto.request.teamrequest;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.mymatch.enums.Urgency;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestCreationRequest {
    @NotBlank
    String title;

    @Size(max = 1000)
    String description;

    Set<Long> skillIds; // optional

    @NotNull
    Urgency urgency = Urgency.NORMAL;
}
