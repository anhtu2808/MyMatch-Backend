package com.mymatch.dto.request.material;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialItemUploadRequest {
    @Schema(description = "Material name")
    String name;

    @Schema(type = "string", format = "binary", description = "File to upload")
    MultipartFile file;
}
