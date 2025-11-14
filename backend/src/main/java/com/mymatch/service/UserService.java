package com.mymatch.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.request.user.UserFilterRequest;
import com.mymatch.dto.request.user.UserUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.enums.RoleType;

public interface UserService {
    public UserResponse createUser(UserCreationRequest request, RoleType roleType);

    UserResponse updateUser(UserUpdateRequest request, Long id);

    public UserResponse deleteUser(Long id);

    @PreAuthorize("hasAuthority('user:update')")
    public UserResponse banUser(Long userId);

    @PreAuthorize("hasAuthority('user:update')")
    public void unBanUser(Long userId);

    @PreAuthorize("hasAuthority('user:read')")
    public UserResponse getUser(Long id);

    public UserResponse getMyInfo();

    @PreAuthorize("hasAuthority('user:read')")
    public PageResponse<UserResponse> getAllUsers(UserFilterRequest filterRequest);
}
