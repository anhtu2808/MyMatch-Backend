package com.mymatch.dto.request.student;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {

    String studentCode;
    Long campusId;

    String skill;
    Double goals;
    String description;
    String major;
}
