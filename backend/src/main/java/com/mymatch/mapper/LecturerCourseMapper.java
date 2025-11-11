package com.mymatch.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mymatch.dto.response.lecturercourse.LecturerCourseResponse;
import com.mymatch.entity.LecturerCourse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LecturerCourseMapper {
    LecturerCourseResponse toResponse(LecturerCourse lecturerCourse);

    List<LecturerCourseResponse> toResponseList(List<LecturerCourse> lecturerCourses);
}
