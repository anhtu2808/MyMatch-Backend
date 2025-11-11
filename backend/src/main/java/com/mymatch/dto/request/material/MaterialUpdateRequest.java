package com.mymatch.dto.request.material;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialUpdateRequest {
    String name;
    String description;
    Long courseId;
    Long lecturerId;

    @Schema(description = "coin for material")
    @Min(value = 3000, message = "Price must be at least 3000")
    @Max(value = 15000, message = "Price must be at most 15000")
    Long price;

    @Schema(format = "binary")
    MultipartFile file;
}
