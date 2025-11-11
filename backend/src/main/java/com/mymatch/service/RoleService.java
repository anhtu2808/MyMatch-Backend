package com.mymatch.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.mymatch.dto.request.role.RoleUpdateRequest;
import com.mymatch.dto.response.role.RoleResponse;
import com.mymatch.entity.Role;
import com.mymatch.enums.RoleType;

public interface RoleService {

    @PreAuthorize("hasAuthority('role:update')")
    RoleResponse updateRole(Long id, RoleUpdateRequest req);

    @PreAuthorize("hasAuthority('role:delete')")
    RoleResponse deleteRole(Long id);

    RoleResponse getById(Long id);

    @PreAuthorize("hasAuthority('role:read')")
    List<RoleResponse> getAllRoles();

    Role getRoleWithPermissions(RoleType roleType);
}
