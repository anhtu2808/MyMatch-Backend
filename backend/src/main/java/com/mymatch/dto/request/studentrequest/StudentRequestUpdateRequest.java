package com.mymatch.dto.request.studentrequest;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.*;

import com.mymatch.enums.RequestStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequestUpdateRequest {
    @NotBlank
    String requestDetail;

    @PositiveOrZero
    Double goal;

    @Size(max = 8)
    String classCode;

    @Size(max = 1000)
    String description;

    @NotNull
    Long courseId;

    @NotNull
    Long semesterId;

    @NotNull
    Long campusId;

    Set<Long> skillIds;
    RequestStatus status;
    LocalDateTime expiresAt;
}
