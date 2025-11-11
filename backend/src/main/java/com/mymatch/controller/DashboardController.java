package com.mymatch.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mymatch.dto.request.dashboard.*;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.dashboard.*;
import com.mymatch.service.DashboardService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {

    DashboardService dashboardService;

    @GetMapping("/kpis")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<DashboardKpiResponse> getKpis(@ModelAttribute DashboardFilterRequest filter) {
        return ApiResponse.<DashboardKpiResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy KPI dashboard thành công")
                .result(dashboardService.getKpis(filter))
                .build();
    }

    @GetMapping("/charts/revenue-trend")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<List<RevenueTrendData>> getRevenueTrend(@ModelAttribute ChartFilterRequest filter) {
        return ApiResponse.<List<RevenueTrendData>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy dữ liệu xu hướng doanh thu thành công")
                .result(dashboardService.getRevenueTrend(filter))
                .build();
    }

    @GetMapping("/charts/user-growth")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<List<UserGrowthData>> getUserGrowth(@ModelAttribute ChartFilterRequest filter) {
        return ApiResponse.<List<UserGrowthData>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy dữ liệu tăng trưởng người dùng thành công")
                .result(dashboardService.getUserGrowth(filter))
                .build();
    }

    @GetMapping("/charts/swap-success-rate")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<SwapSuccessRateData> getSwapSuccessRate(@ModelAttribute DashboardFilterRequest filter) {
        return ApiResponse.<SwapSuccessRateData>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy dữ liệu tỷ lệ thành công swap thành công")
                .result(dashboardService.getSwapSuccessRate(filter))
                .build();
    }

    @GetMapping("/tables/users")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<PageResponse<UserDashboardResponse>> getUsersTable(
            @ModelAttribute UserTableFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<PageResponse<UserDashboardResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách người dùng thành công")
                .result(dashboardService.getUsersTable(filter, page, size, sortBy, sortDirection))
                .build();
    }

    @GetMapping("/tables/students")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<PageResponse<StudentDashboardResponse>> getStudentsTable(
            @ModelAttribute StudentTableFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<PageResponse<StudentDashboardResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sinh viên thành công")
                .result(dashboardService.getStudentsTable(filter, page, size, sortBy, sortDirection))
                .build();
    }

    @GetMapping("/tables/swaps")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<PageResponse<SwapDashboardResponse>> getSwapsTable(
            @ModelAttribute SwapTableFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<PageResponse<SwapDashboardResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách swap thành công")
                .result(dashboardService.getSwapsTable(filter, page, size, sortBy, sortDirection))
                .build();
    }

    @GetMapping("/tables/transactions")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<PageResponse<TransactionDashboardResponse>> getTransactionsTable(
            @ModelAttribute TransactionTableFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<PageResponse<TransactionDashboardResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách giao dịch thành công")
                .result(dashboardService.getTransactionsTable(filter, page, size, sortBy, sortDirection))
                .build();
    }

    @GetMapping("/filters/options")
    @PreAuthorize("hasAuthority('dashboard:read')")
    public ApiResponse<FilterOptionsResponse> getFilterOptions(@RequestParam(required = false) Long universityId) {
        return ApiResponse.<FilterOptionsResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách filter options thành công")
                .result(dashboardService.getFilterOptions(universityId))
                .build();
    }
}
