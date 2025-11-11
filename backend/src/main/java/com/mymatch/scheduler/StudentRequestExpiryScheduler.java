package com.mymatch.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mymatch.repository.StudentRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudentRequestExpiryScheduler {

    private final StudentRequestRepository studentRequestRepository;

    // Chạy vào lúc 23:58 hằng ngày
    @Scheduled(cron = "0 58 23 * * *")
    @Transactional
    public void expireStudentRequests() {
        var now = LocalDateTime.now();
        int n = studentRequestRepository.expireOpenRequests(now);
        if (n > 0) {
            log.info("[student-request-expiry] {} requests -> EXPIRED @{}", n, now);
        }
    }
}
