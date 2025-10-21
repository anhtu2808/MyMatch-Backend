package com.mymatch.service;

import com.mymatch.dto.request.banner.BannerCreateRequest;
import com.mymatch.dto.request.banner.BannerUpdateRequest;
import com.mymatch.dto.response.banner.BannerResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BannerService {
//    @PreAuthorize("hasAuthority('banner:create')")
    BannerResponse createBanner(BannerCreateRequest req);

    BannerResponse getById(Long id);

//    @PreAuthorize("hasAuthority('banner:update')")
    BannerResponse updateBanner(Long id, BannerUpdateRequest req);

//    @PreAuthorize("hasAuthority('banner:delete')")
    void deleteBanner(Long id);

    List<BannerResponse> getAllBanners();
}
