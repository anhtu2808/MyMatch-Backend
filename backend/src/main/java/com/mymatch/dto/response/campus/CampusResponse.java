package com.mymatch.dto.response.campus;

import java.time.LocalDateTime;

import com.mymatch.dto.response.university.UniversityResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampusResponse {
    Long id;
    String name;
    String address;
    String imgUrl;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    UniversityResponse university;
}
