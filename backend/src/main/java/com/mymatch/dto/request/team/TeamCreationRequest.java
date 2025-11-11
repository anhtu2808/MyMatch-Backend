package com.mymatch.dto.request.team;

import java.util.List;

import jakarta.validation.constraints.*;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestCreationRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamCreationRequest {
    @NotBlank(message = "Tên đội không được để trống")
    String name;

    @Positive(message = "Số thành viên phải lớn hơn 0")
    @Max(value = 50, message = "Số thành viên tối đa không được vượt quá 50")
    Integer memberMax;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    String description;

    @NotNull(message = "Học kỳ không được để trống")
    Long semesterId;

    @NotNull(message = "Cơ sở không được để trống")
    Long campusId;

    @NotNull(message = "Môn học không được để trống")
    Long courseId;

    String image;

    @NotNull(message = "Yêu cầu đội không được để trống")
    List<TeamRequestCreationRequest> teamRequest;

    List<MemberCreationRequest> members;
}
