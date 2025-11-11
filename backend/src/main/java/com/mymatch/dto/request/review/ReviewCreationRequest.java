package com.mymatch.dto.request.review;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.mymatch.dto.request.reviewdetail.ReviewDetailRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Request body to create a Review.
 * - IDs refer to existing entities
 * - 'details' holds values for each ReviewCriteria (type-driven usage):
 * * MARK      -> use 'score' (e.g., "8.5" or "9")  [string to keep flexible format]
 * * COMMENT   -> use 'comment'
 * * YES_NO    -> use 'isYes'
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewCreationRequest {

    @NotNull
    Long lecturerId;

    @NotNull
    Long courseId;

    @NotNull
    Long semesterId;

    Boolean isAnonymous;

    String evidenceUrl;

    @NotEmpty
    List<ReviewDetailRequest> details;
}
