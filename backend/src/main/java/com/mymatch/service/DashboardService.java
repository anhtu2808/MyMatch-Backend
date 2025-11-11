package com.mymatch.service;

import java.util.List;

import com.mymatch.dto.request.dashboard.*;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.dashboard.*;

public interface DashboardService {
    DashboardKpiResponse getKpis(DashboardFilterRequest filter);

    List<RevenueTrendData> getRevenueTrend(ChartFilterRequest filter);

    List<UserGrowthData> getUserGrowth(ChartFilterRequest filter);

    SwapSuccessRateData getSwapSuccessRate(DashboardFilterRequest filter);

    PageResponse<UserDashboardResponse> getUsersTable(
            UserTableFilterRequest filter, int page, int size, String sortBy, String sortDirection);

    PageResponse<StudentDashboardResponse> getStudentsTable(
            StudentTableFilterRequest filter, int page, int size, String sortBy, String sortDirection);

    PageResponse<SwapDashboardResponse> getSwapsTable(
            SwapTableFilterRequest filter, int page, int size, String sortBy, String sortDirection);

    PageResponse<TransactionDashboardResponse> getTransactionsTable(
            TransactionTableFilterRequest filter, int page, int size, String sortBy, String sortDirection);

    FilterOptionsResponse getFilterOptions(Long universityId);
}
