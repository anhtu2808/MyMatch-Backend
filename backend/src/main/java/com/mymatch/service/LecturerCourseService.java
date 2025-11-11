package com.mymatch.service;

import java.util.List;

import com.mymatch.dto.request.lecturercourse.LecturerCourseCreationRequest;
import com.mymatch.dto.response.lecturercourse.LecturerCourseResponse;

public interface LecturerCourseService {
    LecturerCourseResponse assign(LecturerCourseCreationRequest req);

    List<LecturerCourseResponse> getByLecturerId(Long lecturerId);
}
