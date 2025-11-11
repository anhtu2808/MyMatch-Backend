package com.mymatch.dto.request.studentrequestskill;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequestSkillBulkRequest {
    @NotEmpty(message = "Danh sách skill không được rỗng")
    Set<Long> skillIds;
}
