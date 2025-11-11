package com.mymatch.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mymatch.common.AbstractAuditingEntity;
import com.mymatch.enums.BannerStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE banner SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Banner extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "TEXT")
    String imageUrl;

    @Column(columnDefinition = "TEXT")
    String linkUrl;

    @Column(nullable = false)
    LocalDateTime startDate;

    @Column(nullable = false)
    LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    BannerStatus status = BannerStatus.INACTIVE;
}
