package com.mymatch.dto.request.reviewcriteria;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCriteriaUpdateRequest {
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
}
