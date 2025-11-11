package com.mymatch.service;

import java.util.List;

import com.mymatch.dto.response.semester.SemesterResponse;

public interface SemesterService {
    List<SemesterResponse> getSemestersByUniversityId(Long universityId);
}
