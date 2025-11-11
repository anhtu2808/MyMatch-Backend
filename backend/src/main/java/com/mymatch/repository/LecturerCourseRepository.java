package com.mymatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.LecturerCourse;

@Repository
public interface LecturerCourseRepository extends JpaRepository<LecturerCourse, Long> {
    boolean existsByLecturer_IdAndCourse_Id(Long lecturerId, Long courseId);

    List<LecturerCourse> findByLecturer_Id(Long lecturerId);
}
