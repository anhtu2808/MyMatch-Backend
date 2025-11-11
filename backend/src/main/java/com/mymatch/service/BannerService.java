package com.mymatch.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.mymatch.dto.request.banner.BannerCreateRequest;
import com.mymatch.dto.request.banner.BannerUpdateRequest;
import com.mymatch.dto.response.banner.BannerResponse;

public interface BannerService {
    @PreAuthorize("hasAuthority('banner:create')")
    BannerResponse createBanner(BannerCreateRequest req);

    BannerResponse getById(Long id);

    @PreAuthorize("hasAuthority('banner:update')")
    BannerResponse updateBanner(Long id, BannerUpdateRequest req);

    @PreAuthorize("hasAuthority('banner:delete')")
    void deleteBanner(Long id);

    List<BannerResponse> getAllBanners();
}
