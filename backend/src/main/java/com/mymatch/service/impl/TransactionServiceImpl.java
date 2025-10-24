package com.mymatch.service.impl;

import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.entity.Transaction;
import com.mymatch.entity.User;
import com.mymatch.entity.Wallet;
import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.enums.TransactionType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.TransactionMapper;
import com.mymatch.repository.TransactionRepository;
import com.mymatch.repository.UserRepository;
import com.mymatch.repository.WalletRepository;
import com.mymatch.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    TransactionMapper transactionManager;
    UserRepository userRepository;
    WalletRepository walletRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transaction initiateTransaction(Wallet wallet, Long coin, Double amountVND, TransactionType type, TransactionSource source, String description) {
        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .coin(coin)
                .amount(amountVND)
                .type(type)
                .source(source)
                .description(description)
                .status(TransactionStatus.PENDING)
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionResponse markAsCompleted(Transaction transaction) {
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
        return transactionManager.toResponse(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionResponse markAsFailed(Transaction transaction, String reason) {
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setDescription(transaction.getDescription() + " | Failed reason: " + reason);
        return transactionManager.toResponse(transactionRepository.save(transaction));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rollbackTransaction(Transaction transaction, String reason) {
        if (transaction == null || transaction.getId() == null) {
            log.warn("Skip rollback because transaction is null or not persisted. Reason: {}", reason);
            return;
        }
        log.info("Rolling back transaction id: {}, reason: {}", transaction.getId(), reason);

        Transaction existing = transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transaction.getId()));

        existing.setStatus(TransactionStatus.FAILED);
        existing.setDescription(existing.getDescription() + " | Rolled back reason: " + reason);

        transactionRepository.save(existing);
    }

    @Override
    public PageResponse<TransactionResponse> getMyTransactions(int page, int size, String sortBy, String sortDirection) {
        // Get current authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem user có wallet không
        if (user.getWallet() == null) {
            throw new AppException(ErrorCode.WALLET_NOT_FOUND);
        }

        Long walletId = user.getWallet().getId();

        // Check if wallet exists
        walletRepository.findById(walletId)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));

        // Pagination logic
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String sort = (sortBy == null || sortBy.isBlank()) ? "createAt" : sortBy;

        Pageable pageable = PageRequest.of(
                Math.max(page - 1, 0),
                size,
                Sort.by(direction, sort)
        );

        Page<Transaction> pages = transactionRepository.findByWalletId(walletId, pageable);
        List<TransactionResponse> data = pages.getContent().stream()
                .map(transactionManager::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<TransactionResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
