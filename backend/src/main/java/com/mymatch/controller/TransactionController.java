package com.mymatch.controller;

import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {
    TransactionService transactionService;

    @GetMapping("/my-transactions")
    public ApiResponse<PageResponse<TransactionResponse>> getMyTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createAt") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {

        return ApiResponse.<PageResponse<TransactionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách giao dịch thành công")
                .result(transactionService.getMyTransactions(page, size, sortBy, sortDirection))
                .build();
    }
}
