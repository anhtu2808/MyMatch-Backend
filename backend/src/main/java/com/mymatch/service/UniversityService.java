package com.mymatch.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.mymatch.dto.request.university.UniversityCreationRequest;
import com.mymatch.dto.request.university.UniversityUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.university.UniversityResponse;

public interface UniversityService {
    //    @PreAuthorize("hasAuthority('university:create')")
    UniversityResponse createUniversity(UniversityCreationRequest req);

    UniversityResponse getById(Long id);

    @PreAuthorize("hasAuthority('university:update')")
    UniversityResponse updateUniversity(Long id, UniversityUpdateRequest req);

    @PreAuthorize("hasAuthority('university:delete')")
    UniversityResponse deleteUniversity(Long id);

    PageResponse<UniversityResponse> getAllUniversities(int page, int size, String sort);
}
