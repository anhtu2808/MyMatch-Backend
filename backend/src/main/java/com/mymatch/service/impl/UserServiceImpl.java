package com.mymatch.service.impl;

import static com.mymatch.utils.SecurityUtil.hasAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.request.user.UserFilterRequest;
import com.mymatch.dto.request.user.UserUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.RoleType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.StudentMapper;
import com.mymatch.mapper.UserMapper;
import com.mymatch.repository.*;
import com.mymatch.service.StudentService;
import com.mymatch.service.UserService;
import com.mymatch.specification.UserSpecification;
import com.mymatch.utils.SecurityUtil;
import com.mymatch.utils.WalletCodeUtil;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    CampusRepository campusRepository;
    StudentRepository studentRepository;
    StudentService studentService;
    StudentMapper studentMapper;
    WalletCodeUtil codeUtil;
    WalletRepository walletRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request, RoleType roleType) {
        if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.EMAIL_EXISTED);
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        Wallet wallet = Wallet.builder().coin(0L).build();
        String walletCode;
        do walletCode = codeUtil.randomBase();
        while (walletRepository.existsByCode(walletCode));
        wallet.setCode(walletCode);
        wallet = walletRepository.save(wallet);

        User user = userMapper.toUser(request, role, hashedPassword);
        user.setWallet(wallet);

        if (roleType == RoleType.STUDENT) {
            Student student = studentRepository.save(studentMapper.toEntity(request.getStudentCreationRequest()));
            user.setStudent(student);
        }

        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User existingUser = userRepository
                .findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!hasAuthority("user:update")) {
            if (!existingUser.getId().equals(user.getId())) {
                throw new AppException(ErrorCode.FORBIDDEN);
            }
        }
        userMapper.toUser(user, request);
        if (request.getCampusId() != null) {
            Campus campus = campusRepository
                    .findById(request.getCampusId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_EXISTED));
            user.getStudent().setCampus(campus);
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setIsActive(false);
        user = userRepository.save(user);
        log.info("Banned user id={}, username={}", userId, user.getUsername());
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void unBanUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setIsActive(true);
        userRepository.save(user);
        log.info("Unbanned user id={}, username={}", userId, user.getUsername());
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            var expiresAt = jwtAuth.getToken().getExpiresAt();
            if (expiresAt != null && expiresAt.isBefore(java.time.Instant.now())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
        }
        String username = authentication.getName();
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Set<String> permissions = user.getRole().getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        Student student = user.getStudent();
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setPermissions(permissions);
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAllUsers(UserFilterRequest filterRequest) {
        Specification<User> spec = UserSpecification.withFilter(filterRequest);

        String sortBy =
                (filterRequest.getSort() == null || filterRequest.getSort().isBlank()) ? "id" : filterRequest.getSort();
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        int page = Math.max(filterRequest.getPage() - 1, 0);
        int size = Math.min(Math.max(filterRequest.getSize(), 1), 200);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> pages = userRepository.findAll(spec, pageable);

        List<UserResponse> data = pages.getContent().stream()
                .map(user -> {
                    UserResponse response = userMapper.toUserResponse(user);
                    Set<String> permissions = user.getRole().getPermissions().stream()
                            .map(Permission::getName)
                            .collect(Collectors.toSet());
                    response.setPermissions(permissions);
                    return response;
                })
                .collect(Collectors.toList());

        return PageResponse.<UserResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(filterRequest.getPage())
                .build();
    }
}
