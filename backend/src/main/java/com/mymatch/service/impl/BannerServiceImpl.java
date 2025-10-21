package com.mymatch.service.impl;

import com.mymatch.dto.request.banner.BannerCreateRequest;
import com.mymatch.dto.request.banner.BannerUpdateRequest;
import com.mymatch.dto.response.banner.BannerResponse;
import com.mymatch.entity.Banner;
import com.mymatch.enums.BannerStatus;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.BannerMapper;
import com.mymatch.repository.BannerRepository;
import com.mymatch.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BannerServiceImpl implements BannerService {

    BannerRepository bannerRepository;
    BannerMapper bannerMapper;

    @Override
    @Transactional
    public BannerResponse createBanner(BannerCreateRequest req) {
        Banner banner = bannerMapper.toEntity(req);

        // Tự động set status dựa trên startDate và endDate
        LocalDateTime now = LocalDateTime.now();
        if (banner.getEndDate().isBefore(now)) {
            banner.setStatus(BannerStatus.EXPIRED);
        } else if (banner.getStartDate().isAfter(now)) {
            banner.setStatus(BannerStatus.INACTIVE);
        } else {
            banner.setStatus(BannerStatus.ACTIVE);
        }

        banner = bannerRepository.save(banner);
        log.info("Created banner id={}, title={}, status={}", banner.getId(), banner.getTitle(), banner.getStatus());
        return bannerMapper.toResponse(banner);
    }

    @Override
    public BannerResponse getById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));
        return bannerMapper.toResponse(banner);
    }

    @Override
    @Transactional
    public BannerResponse updateBanner(Long id, BannerUpdateRequest req) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));

        bannerMapper.updateEntity(banner, req);

        // Cập nhật lại status nếu startDate hoặc endDate thay đổi
        LocalDateTime now = LocalDateTime.now();
        if (banner.getEndDate().isBefore(now)) {
            banner.setStatus(BannerStatus.EXPIRED);
        } else if (banner.getStartDate().isAfter(now)) {
            banner.setStatus(BannerStatus.INACTIVE);
        } else {
            banner.setStatus(BannerStatus.ACTIVE);
        }

        banner = bannerRepository.save(banner);
        log.info("Updated banner id={}, status={}", id, banner.getStatus());
        return bannerMapper.toResponse(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));
        bannerRepository.delete(banner);
        log.info("Deleted banner id={}", id);
    }

    @Override
    public List<BannerResponse> getAllBanners() {
        List<Banner> banners = bannerRepository.findAll();
        return banners.stream()
                .map(bannerMapper::toResponse)
                .collect(Collectors.toList());
    }
}
