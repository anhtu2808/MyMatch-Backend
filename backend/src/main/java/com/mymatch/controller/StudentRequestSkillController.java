package com.mymatch.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mymatch.dto.request.studentrequestskill.StudentRequestSkillBulkRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;
import com.mymatch.service.StudentRequestSkillService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/student-request-skills")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentRequestSkillController {
    StudentRequestSkillService studentRequestSkillService;

    @PutMapping("/{requestId}")
    public ApiResponse<List<StudentRequestSkillResponse>> replaceAll(
            @PathVariable Long requestId, @RequestBody StudentRequestSkillBulkRequest req) {
        return ApiResponse.<List<StudentRequestSkillResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật kỹ năng thành công")
                .result(studentRequestSkillService.replace(requestId, req))
                .build();
    }
}
