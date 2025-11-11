package com.mymatch.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mymatch.entity.Banner;
import com.mymatch.enums.BannerStatus;
import com.mymatch.repository.BannerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BannerScheduler {

    private final BannerRepository bannerRepository;

    /**
     * Chạy vào lúc 23:59 hằng ngày để cập nhật trạng thái banner
     * Cron expression: "0 59 23 * * *" = 59 phút, 23 giờ (11:59 PM), mỗi ngày
     */
    @Scheduled(cron = "0 59 23 * * *")
    @Transactional
    public void updateBannerStatus() {
        log.info("=== Starting banner status update task ===");

        LocalDateTime now = LocalDateTime.now();
        int totalUpdated = 0;

        // 1. Cập nhật banners đã hết hạn (endDate < now)
        List<Banner> expiredBanners = bannerRepository.findExpiredBanners(now);
        for (Banner banner : expiredBanners) {
            banner.setStatus(BannerStatus.EXPIRED);
            totalUpdated++;
            log.info("Banner ID {} status changed to EXPIRED (endDate: {})", banner.getId(), banner.getEndDate());
        }
        if (!expiredBanners.isEmpty()) {
            bannerRepository.saveAll(expiredBanners);
        }

        // 2. Cập nhật banners đang hoạt động (startDate <= now <= endDate)
        List<Banner> activeBanners = bannerRepository.findActiveBanners(now);
        for (Banner banner : activeBanners) {
            banner.setStatus(BannerStatus.ACTIVE);
            totalUpdated++;
            log.info(
                    "Banner ID {} status changed to ACTIVE (startDate: {}, endDate: {})",
                    banner.getId(),
                    banner.getStartDate(),
                    banner.getEndDate());
        }
        if (!activeBanners.isEmpty()) {
            bannerRepository.saveAll(activeBanners);
        }

        // 3. Cập nhật banners chưa đến thời gian hoạt động (startDate > now)
        List<Banner> inactiveBanners = bannerRepository.findInactiveBanners(now);
        for (Banner banner : inactiveBanners) {
            banner.setStatus(BannerStatus.INACTIVE);
            totalUpdated++;
            log.info("Banner ID {} status changed to INACTIVE (startDate: {})", banner.getId(), banner.getStartDate());
        }
        if (!inactiveBanners.isEmpty()) {
            bannerRepository.saveAll(inactiveBanners);
        }

        log.info("=== Banner status update task completed. Total updated: {} ===", totalUpdated);
    }
}
