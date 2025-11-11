package com.mymatch.dto.response.purchase;

import java.time.LocalDateTime;

import com.mymatch.dto.response.plan.PlanResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.enums.PurchaseStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPurchaseResponse {
    Long id;
    PlanResponse plan;
    UserResponse user;
    Double costCoin;
    LocalDateTime purchaseDate;
    LocalDateTime expiryDate;

    PurchaseStatus status;
}
