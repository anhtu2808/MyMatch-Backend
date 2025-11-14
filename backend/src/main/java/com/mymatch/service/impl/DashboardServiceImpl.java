package com.mymatch.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mymatch.dto.request.dashboard.*;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.dashboard.*;
import com.mymatch.entity.*;
import com.mymatch.enums.*;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.repository.*;
import com.mymatch.service.DashboardService;
import com.mymatch.specification.DashboardSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardServiceImpl implements DashboardService {

    UserRepository userRepository;
    StudentRepository studentRepository;
    SwapRepository swapRepository;
    TransactionRepository transactionRepository;
    StudentRequestRepository studentRequestRepository;
    TeamRequestRepository teamRequestRepository;
    UniversityRepository universityRepository;
    CampusRepository campusRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardKpiResponse getKpis(DashboardFilterRequest filter) {
        Specification<User> userSpec = DashboardSpecification.userFilter(filter);
        Specification<Student> studentSpec = DashboardSpecification.studentFilter(filter);
        Specification<Swap> swapSpec = DashboardSpecification.swapFilter(filter);

        // Total users
        long totalUsers = userRepository.count(userSpec);

        // Revenue - sum of completed TOP_UP transactions
        Specification<Transaction> revenueSpec = DashboardSpecification.transactionFilter(filter);
        Specification<Transaction> topUpCompletedSpec = revenueSpec.and((root, query, cb) -> cb.and(
                cb.equal(root.get("source"), TransactionSource.TOP_UP),
                cb.equal(root.get("status"), TransactionStatus.COMPLETED)));
        double revenue = transactionRepository.findAll(topUpCompletedSpec).stream()
                .mapToDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                .sum();

        // Active students - students with active users
        Specification<Student> activeStudentSpec =
                studentSpec.and((root, query, cb) -> cb.equal(root.join("user").get("isActive"), true));
        long activeStudents = studentRepository.count(activeStudentSpec);

        // Pending actions
        Specification<Swap> pendingSwapSpec =
                swapSpec.and((root, query, cb) -> cb.equal(root.get("status"), SwapStatus.PENDING));
        long pendingSwaps = swapRepository.count(pendingSwapSpec);

        Specification<StudentRequest> pendingStudentRequestSpec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), RequestStatus.OPEN));
            if (filter != null) {
                if (filter.getStartDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
                }
                if (filter.getEndDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
                }
                if (filter.getUniversityId() != null) {
                    predicates.add(cb.equal(
                            root.join("student")
                                    .join("campus")
                                    .get("university")
                                    .get("id"),
                            filter.getUniversityId()));
                }
                if (filter.getCampusId() != null) {
                    predicates.add(cb.equal(root.join("student").get("campus").get("id"), filter.getCampusId()));
                }
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
        long pendingStudentRequests = studentRequestRepository.count(pendingStudentRequestSpec);

        Specification<TeamRequest> pendingTeamRequestSpec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), RequestStatus.OPEN));
            if (filter != null) {
                if (filter.getStartDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
                }
                if (filter.getEndDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
                }
                if (filter.getUniversityId() != null) {
                    predicates.add(cb.equal(
                            root.join("team").join("campus").get("university").get("id"), filter.getUniversityId()));
                }
                if (filter.getCampusId() != null) {
                    predicates.add(cb.equal(root.join("team").get("campus").get("id"), filter.getCampusId()));
                }
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
        long pendingTeamRequests = teamRequestRepository.count(pendingTeamRequestSpec);

        long pendingActions = pendingSwaps + pendingStudentRequests + pendingTeamRequests;

        // Total swaps
        long totalSwaps = swapRepository.count(swapSpec);

        // Total transactions
        Specification<Transaction> transactionSpec = DashboardSpecification.transactionFilter(filter);
        long totalTransactions = transactionRepository.count(transactionSpec);

        return DashboardKpiResponse.builder()
                .totalUsers(totalUsers)
                .revenue(revenue)
                .activeStudents(activeStudents)
                .pendingActions(pendingActions)
                .totalSwaps(totalSwaps)
                .totalTransactions(totalTransactions)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevenueTrendData> getRevenueTrend(ChartFilterRequest filter) {
        Specification<Transaction> baseSpec = DashboardSpecification.transactionFilter(filter);
        Specification<Transaction> topUpCompletedSpec = baseSpec.and((root, query, cb) -> cb.and(
                cb.equal(root.get("source"), TransactionSource.TOP_UP),
                cb.equal(root.get("status"), TransactionStatus.COMPLETED)));

        List<Transaction> transactions = transactionRepository.findAll(topUpCompletedSpec);

        if (transactions.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDateTime startDate = filter.getStartDate() != null
                ? filter.getStartDate()
                : transactions.stream()
                        .map(Transaction::getCreateAt)
                        .min(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.now());
        LocalDateTime endDate = filter.getEndDate() != null ? filter.getEndDate() : LocalDateTime.now();

        DateGroupBy groupBy = filter.getGroupBy() != null ? filter.getGroupBy() : DateGroupBy.DAY;

        return groupTransactionsByDate(transactions, startDate, endDate, groupBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGrowthData> getUserGrowth(ChartFilterRequest filter) {
        Specification<User> userSpec = DashboardSpecification.userFilter(filter);
        List<User> users = userRepository.findAll(userSpec);

        if (users.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDateTime startDate = filter.getStartDate() != null
                ? filter.getStartDate()
                : users.stream()
                        .map(User::getCreateAt)
                        .min(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.now());
        LocalDateTime endDate = filter.getEndDate() != null ? filter.getEndDate() : LocalDateTime.now();

        DateGroupBy groupBy = filter.getGroupBy() != null ? filter.getGroupBy() : DateGroupBy.DAY;

        return groupUsersByDate(users, startDate, endDate, groupBy);
    }

    @Override
    @Transactional(readOnly = true)
    public SwapSuccessRateData getSwapSuccessRate(DashboardFilterRequest filter) {
        Specification<Swap> swapSpec = DashboardSpecification.swapFilter(filter);

        List<Swap> swaps = swapRepository.findAll(swapSpec);

        long totalSwaps = swaps.size();
        long approvedSwaps =
                swaps.stream().filter(s -> s.getStatus() == SwapStatus.APPROVED).count();
        long rejectedSwaps =
                swaps.stream().filter(s -> s.getStatus() == SwapStatus.REJECTED).count();
        long pendingSwaps =
                swaps.stream().filter(s -> s.getStatus() == SwapStatus.PENDING).count();

        double successRate = totalSwaps > 0 ? (double) approvedSwaps / totalSwaps * 100.0 : 0.0;

        return SwapSuccessRateData.builder()
                .totalSwaps(totalSwaps)
                .approvedSwaps(approvedSwaps)
                .rejectedSwaps(rejectedSwaps)
                .pendingSwaps(pendingSwaps)
                .successRate(successRate)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserDashboardResponse> getUsersTable(
            UserTableFilterRequest filter, int page, int size, String sortBy, String sortDirection) {
        Specification<User> spec = DashboardSpecification.userFilter(filter);

        if (filter.getIsActive() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isActive"), filter.getIsActive()));
        }
        if (filter.getRoleType() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("role").get("name"), filter.getRoleType()));
        }

        Sort.Direction direction =
                Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isBlank()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(size, 1), sort);

        Page<User> pages = userRepository.findAll(spec, pageable);

        List<UserDashboardResponse> data = pages.getContent().stream()
                .map(this::mapToUserDashboardResponse)
                .collect(Collectors.toList());

        return PageResponse.<UserDashboardResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StudentDashboardResponse> getStudentsTable(
            StudentTableFilterRequest filter, int page, int size, String sortBy, String sortDirection) {
        Specification<Student> spec = DashboardSpecification.studentFilter(filter);

        if (filter.getIsActive() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("user").get("isActive"), filter.getIsActive()));
        }

        Sort.Direction direction =
                Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isBlank()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(size, 1), sort);

        Page<Student> pages = studentRepository.findAll(spec, pageable);

        List<StudentDashboardResponse> data = pages.getContent().stream()
                .map(this::mapToStudentDashboardResponse)
                .collect(Collectors.toList());

        return PageResponse.<StudentDashboardResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SwapDashboardResponse> getSwapsTable(
            SwapTableFilterRequest filter, int page, int size, String sortBy, String sortDirection) {
        Specification<Swap> spec = DashboardSpecification.swapFilter(filter);

        if (filter.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }

        Sort.Direction direction =
                Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isBlank()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(size, 1), sort);

        Page<Swap> pages = swapRepository.findAll(spec, pageable);

        List<SwapDashboardResponse> data = pages.getContent().stream()
                .map(this::mapToSwapDashboardResponse)
                .collect(Collectors.toList());

        return PageResponse.<SwapDashboardResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TransactionDashboardResponse> getTransactionsTable(
            TransactionTableFilterRequest filter, int page, int size, String sortBy, String sortDirection) {
        Specification<Transaction> spec = DashboardSpecification.transactionFilter(filter);

        if (filter.getType() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), filter.getType()));
        }
        if (filter.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }
        if (filter.getSource() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("source"), filter.getSource()));
        }

        Sort.Direction direction =
                Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
        Sort sort = Sort.by(direction, (sortBy != null && !sortBy.isBlank()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(size, 1), sort);

        Page<Transaction> pages = transactionRepository.findAll(spec, pageable);

        List<TransactionDashboardResponse> data = pages.getContent().stream()
                .map(this::mapToTransactionDashboardResponse)
                .collect(Collectors.toList());

        return PageResponse.<TransactionDashboardResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public FilterOptionsResponse getFilterOptions(Long universityId) {
        List<UniversityOption> universities = universityRepository.findAll().stream()
                .map(u -> UniversityOption.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build())
                .collect(Collectors.toList());

        List<CampusOption> campuses;
        if (universityId != null) {
            campuses = campusRepository.findAll().stream()
                    .filter(c -> c.getUniversity().getId().equals(universityId))
                    .map(c -> CampusOption.builder()
                            .id(c.getId())
                            .name(c.getName())
                            .universityId(c.getUniversity().getId())
                            .universityName(c.getUniversity().getName())
                            .build())
                    .collect(Collectors.toList());
        } else {
            campuses = campusRepository.findAll().stream()
                    .map(c -> CampusOption.builder()
                            .id(c.getId())
                            .name(c.getName())
                            .universityId(c.getUniversity().getId())
                            .universityName(c.getUniversity().getName())
                            .build())
                    .collect(Collectors.toList());
        }

        return FilterOptionsResponse.builder()
                .universities(universities)
                .campuses(campuses)
                .build();
    }

    // Helper methods

    private List<RevenueTrendData> groupTransactionsByDate(
            List<Transaction> transactions, LocalDateTime startDate, LocalDateTime endDate, DateGroupBy groupBy) {
        List<RevenueTrendData> result = new ArrayList<>();
        LocalDate current = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();

        while (!current.isAfter(end)) {
            LocalDateTime periodStart = current.atStartOfDay();
            LocalDateTime periodEnd = getPeriodEnd(current, groupBy);

            double revenue = transactions.stream()
                    .filter(t -> {
                        LocalDateTime createAt = t.getCreateAt();
                        return createAt != null && !createAt.isBefore(periodStart) && createAt.isBefore(periodEnd);
                    })
                    .mapToDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                    .sum();

            result.add(RevenueTrendData.builder()
                    .date(periodStart)
                    .revenue(revenue)
                    .build());

            current = getNextPeriodStart(current, groupBy);
        }

        return result;
    }

    private List<UserGrowthData> groupUsersByDate(
            List<User> users, LocalDateTime startDate, LocalDateTime endDate, DateGroupBy groupBy) {
        List<UserGrowthData> result = new ArrayList<>();
        LocalDate current = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();
        long cumulativeTotal = 0;

        while (!current.isAfter(end)) {
            LocalDateTime periodStart = current.atStartOfDay();
            LocalDateTime periodEnd = getPeriodEnd(current, groupBy);

            long newUsers = users.stream()
                    .filter(u -> {
                        LocalDateTime createAt = u.getCreateAt();
                        return createAt != null && !createAt.isBefore(periodStart) && createAt.isBefore(periodEnd);
                    })
                    .count();

            cumulativeTotal += newUsers;

            result.add(UserGrowthData.builder()
                    .date(periodStart)
                    .newUsers(newUsers)
                    .totalUsers(cumulativeTotal)
                    .build());

            current = getNextPeriodStart(current, groupBy);
        }

        return result;
    }

    private LocalDateTime getPeriodEnd(LocalDate date, DateGroupBy groupBy) {
        return switch (groupBy) {
            case DAY -> date.plusDays(1).atStartOfDay();
            case WEEK -> date.plusWeeks(1).atStartOfDay();
            case MONTH -> date.plusMonths(1).atStartOfDay();
        };
    }

    private LocalDate getNextPeriodStart(LocalDate date, DateGroupBy groupBy) {
        return switch (groupBy) {
            case DAY -> date.plusDays(1);
            case WEEK -> date.plusWeeks(1);
            case MONTH -> date.plusMonths(1);
        };
    }

    private UserDashboardResponse mapToUserDashboardResponse(User user) {
        String roleName = user.getRole() != null ? user.getRole().getName().toString() : null;
        Long campusId = null;
        String campusName = null;
        Long universityId = null;
        String universityName = null;

        if (user.getStudent() != null && user.getStudent().getCampus() != null) {
            Campus campus = user.getStudent().getCampus();
            campusId = campus.getId();
            campusName = campus.getName();
            if (campus.getUniversity() != null) {
                universityId = campus.getUniversity().getId();
                universityName = campus.getUniversity().getName();
            }
        }

        return UserDashboardResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .role(roleName)
                .isActive(user.getIsActive())
                .campusId(campusId)
                .campusName(campusName)
                .universityId(universityId)
                .universityName(universityName)
                .createAt(user.getCreateAt())
                .build();
    }

    private StudentDashboardResponse mapToStudentDashboardResponse(Student student) {
        User user = student.getUser();
        Long campusId = null;
        String campusName = null;
        Long universityId = null;
        String universityName = null;

        if (student.getCampus() != null) {
            Campus campus = student.getCampus();
            campusId = campus.getId();
            campusName = campus.getName();
            if (campus.getUniversity() != null) {
                universityId = campus.getUniversity().getId();
                universityName = campus.getUniversity().getName();
            }
        }

        return StudentDashboardResponse.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .firstName(user != null ? user.getFirstName() : null)
                .lastName(user != null ? user.getLastName() : null)
                .email(user != null ? user.getEmail() : null)
                .isActive(user != null ? user.getIsActive() : null)
                .campusId(campusId)
                .campusName(campusName)
                .universityId(universityId)
                .universityName(universityName)
                .major(student.getMajor())
                .createAt(student.getCreateAt())
                .build();
    }

    private SwapDashboardResponse mapToSwapDashboardResponse(Swap swap) {
        Student studentFrom = swap.getStudentFrom();
        Student studentTo = swap.getStudentTo();

        String studentFromName = studentFrom != null && studentFrom.getUser() != null
                ? (studentFrom.getUser().getFirstName() + " "
                                + studentFrom.getUser().getLastName())
                        .trim()
                : null;
        String studentToName = studentTo != null && studentTo.getUser() != null
                ? (studentTo.getUser().getFirstName() + " "
                                + studentTo.getUser().getLastName())
                        .trim()
                : null;

        Long campusId = null;
        String campusName = null;
        Long universityId = null;
        String universityName = null;

        if (studentFrom != null && studentFrom.getCampus() != null) {
            Campus campus = studentFrom.getCampus();
            campusId = campus.getId();
            campusName = campus.getName();
            if (campus.getUniversity() != null) {
                universityId = campus.getUniversity().getId();
                universityName = campus.getUniversity().getName();
            }
        }

        return SwapDashboardResponse.builder()
                .id(swap.getId())
                .status(swap.getStatus())
                .studentFromName(studentFromName)
                .studentToName(studentToName)
                .campusId(campusId)
                .campusName(campusName)
                .universityId(universityId)
                .universityName(universityName)
                .createAt(swap.getCreateAt())
                .matchedAt(swap.getMatchedAt())
                .build();
    }

    private TransactionDashboardResponse mapToTransactionDashboardResponse(Transaction transaction) {
        Wallet wallet = transaction.getWallet();
        User user = wallet != null ? wallet.getUser() : null;
        Student student = user != null ? user.getStudent() : null;
        Campus campus = student != null ? student.getCampus() : null;

        Long campusId = null;
        String campusName = null;
        Long universityId = null;
        String universityName = null;

        if (campus != null) {
            campusId = campus.getId();
            campusName = campus.getName();
            if (campus.getUniversity() != null) {
                universityId = campus.getUniversity().getId();
                universityName = campus.getUniversity().getName();
            }
        }

        return TransactionDashboardResponse.builder()
                .id(transaction.getId())
                .transactionCode(transaction.getTransactionCode())
                .coin(transaction.getCoin())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .source(transaction.getSource())
                .description(transaction.getDescription())
                .userName(user != null ? user.getUsername() : null)
                .userEmail(user != null ? user.getEmail() : null)
                .campusId(campusId)
                .campusName(campusName)
                .universityId(universityId)
                .universityName(universityName)
                .createAt(transaction.getCreateAt())
                .build();
    }
}
