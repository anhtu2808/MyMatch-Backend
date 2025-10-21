package com.mymatch.dto.request.banner;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerUpdateRequest {
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    String title;

    String imageUrl;

    String linkUrl;

    LocalDateTime startDate;

    LocalDateTime endDate;
}
