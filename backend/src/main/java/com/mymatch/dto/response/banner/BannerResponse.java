package com.mymatch.dto.response.banner;

import com.mymatch.enums.BannerStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerResponse {
    Long id;
    String title;
    String imageUrl;
    String linkUrl;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BannerStatus status;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
