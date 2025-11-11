package com.mymatch.dto.response.studentrequest;

import java.time.LocalDateTime;
import java.util.Set;

import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.semester.SemesterResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;
import com.mymatch.enums.RequestStatus;

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
public class StudentRequestResponse {
    Long id;
    StudentResponse student;
    String requestDetail;
    Double goal;
    String classCode;
    CourseResponse course;
    SemesterResponse semester;
    CampusResponse campus;
    String description;
    RequestStatus status;
    LocalDateTime createAt;
    LocalDateTime expiresAt;
    Set<StudentRequestSkillResponse> skills;
}
