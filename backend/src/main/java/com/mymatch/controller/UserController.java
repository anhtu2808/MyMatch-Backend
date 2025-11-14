package com.mymatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.request.user.UserFilterRequest;
import com.mymatch.dto.request.user.UserUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.enums.RoleType;
import com.mymatch.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserCreationRequest request) {
        var result = userService.createUser(request, RoleType.STUDENT);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user:create')")
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        var result = userService.createUser(request, request.getRoleType());
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        var result = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ApiResponse<PageResponse<UserResponse>> getAllUsers(@ModelAttribute UserFilterRequest filterRequest) {
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách người dùng thành công")
                .result(userService.getAllUsers(filterRequest))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin người dùng thành công")
                .result(userService.getUser(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật người dùng thành công")
                .result(userService.updateUser(request, id))
                .build();
    }

    @PutMapping("/{id}/ban")
    @PreAuthorize("hasAuthority('user:update')")
    public ApiResponse<UserResponse> banUser(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cấm người dùng thành công")
                .result(userService.banUser(id))
                .build();
    }

    @PutMapping("/{id}/unban")
    @PreAuthorize("hasAuthority('user:update')")
    public ApiResponse<Void> unbanUser(@PathVariable Long id) {
        userService.unBanUser(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Bỏ cấm người dùng thành công")
                .build();
    }
}
