package com.mymatch.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mymatch.repository.SwapRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SwapRequestExpiryScheduler {

    private final SwapRequestRepository swapRequestRepository;

    // Chạy vào lúc 23:57 hằng ngày
    @Scheduled(cron = "0 57 23 * * *")
    @Transactional
    public void expireSwapRequests() {
        var now = LocalDateTime.now();
        int expired = swapRequestRepository.expireSentRequests(now);
        if (expired > 0) {
            log.info("[swap-expiry] {} requests -> EXPIRED @{}", expired, now);
        }
    }
}
