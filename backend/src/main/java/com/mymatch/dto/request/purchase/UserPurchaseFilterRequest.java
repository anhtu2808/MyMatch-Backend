package com.mymatch.dto.request.purchase;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.mymatch.enums.PurchaseStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserPurchaseFilterRequest {

    Long userId;
    Long planId;
    PurchaseStatus status;

    // khoảng thời gian mua
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime purchasedFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime purchasedTo;

    // khoảng thời gian hết hạn
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime expiryFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime expiryTo;

    // Nếu true: chỉ lấy đang còn hiệu lực (status=ACTIVE && expiryDate>=now)
    Boolean activeNow;
}
