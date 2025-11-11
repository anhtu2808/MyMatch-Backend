package com.mymatch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("SELECT b FROM Banner b WHERE b.endDate < :now AND b.status != 'EXPIRED'")
    List<Banner> findExpiredBanners(LocalDateTime now);

    @Query("SELECT b FROM Banner b WHERE b.startDate <= :now AND b.endDate >= :now AND b.status != 'ACTIVE'")
    List<Banner> findActiveBanners(LocalDateTime now);

    @Query("SELECT b FROM Banner b WHERE b.startDate > :now AND b.status != 'INACTIVE'")
    List<Banner> findInactiveBanners(LocalDateTime now);
}
