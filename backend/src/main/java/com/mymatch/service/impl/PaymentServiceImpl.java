package com.mymatch.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mymatch.dto.request.payment.SepayWebhookDTO;
import com.mymatch.dto.response.payment.PaymentResponse;
import com.mymatch.entity.Wallet;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.repository.TransactionRepository;
import com.mymatch.repository.WalletRepository;
import com.mymatch.service.PaymentService;
import com.mymatch.service.WalletService;
import com.mymatch.utils.SecurityUtil;
import com.mymatch.utils.WalletCodeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final WalletCodeUtil codeUtil;

    @Value("${sepay.bank.code}")
    private String bankCode;

    @NotNull
    @Value("${sepay.account.number}")
    private String accountNumber;

    @NotNull
    @Value("${sepay.account.name}")
    private String accountName;

    @NotNull
    @Value("${sepay.qr.base-url}")
    private String qrCodeBaseUrl;

    @Override
    @Transactional
    public PaymentResponse createQrCodePayment() {
        var userId = SecurityUtil.getCurrentUserId();
        Wallet wallet =
                walletRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String code = codeUtil.toFull(wallet.getCode());

        String qrUrl = qrCodeBaseUrl
                + "/img?acc=" + URLEncoder.encode(accountNumber, StandardCharsets.UTF_8)
                + "&bank=" + URLEncoder.encode(bankCode, StandardCharsets.UTF_8)
                + "&des=" + URLEncoder.encode(code, StandardCharsets.UTF_8);

        return PaymentResponse.builder()
                .qrUrl(qrUrl)
                .accountNumber(accountNumber)
                .accountName(accountName)
                .bankCode(bankCode)
                .content(code)
                .build();
    }

    @Override
    @Transactional
    public void handleSepayPaymentHook(SepayWebhookDTO sepayWebhookDTO) {
        log.info("Received webhook: {}", sepayWebhookDTO);
        if (!"in".equals(sepayWebhookDTO.getTransferType())) {
            log.warn("Ignore out transfer: {}", sepayWebhookDTO.getId());
            return;
        }
        String code = sepayWebhookDTO.getCode();
        if (sepayWebhookDTO.getCode() == null || sepayWebhookDTO.getCode().isBlank()) {
            log.warn("Ignore transfer without payment code: {}", sepayWebhookDTO.getId());
            return;
        }
        walletService.topUpWallet(code, sepayWebhookDTO.getTransferAmount().doubleValue());
    }
}
