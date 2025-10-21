package com.mymatch.controller;

import com.mymatch.dto.request.banner.BannerCreateRequest;
import com.mymatch.dto.request.banner.BannerUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.banner.BannerResponse;
import com.mymatch.service.BannerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BannerController {

    BannerService bannerService;

    @PostMapping
    public ApiResponse<BannerResponse> createBanner(@Valid @RequestBody BannerCreateRequest req) {
        return ApiResponse.<BannerResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo banner thành công")
                .result(bannerService.createBanner(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BannerResponse> getById(@PathVariable Long id) {
        return ApiResponse.<BannerResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin banner thành công")
                .result(bannerService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BannerResponse> updateBanner(@PathVariable Long id, @Valid @RequestBody BannerUpdateRequest req) {
        return ApiResponse.<BannerResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật banner thành công")
                .result(bannerService.updateBanner(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá banner thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BannerResponse>> getAllBanners() {
        return ApiResponse.<List<BannerResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách banner thành công")
                .result(bannerService.getAllBanners())
                .build();
    }

}
