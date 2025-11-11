package com.mymatch.dto.request.material;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialCreationRequest {
    @Schema(description = "Material name")
    String name;

    @Schema(description = "Material description")
    String description;

    @Schema(description = "Course ID")
    Long courseId;

    @Schema(description = "Lecturer ID")
    Long lecturerId;

    @Schema(description = "coin for material")
    @Min(value = 3000, message = "Price must be at least 3000")
    @Max(value = 15000, message = "Price must be at most 15000")
    Long price;

    List<Long> materialItemIds;
}
