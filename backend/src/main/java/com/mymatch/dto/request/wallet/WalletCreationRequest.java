package com.mymatch.dto.request.wallet;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletCreationRequest {
    @NotNull(message = "Số dư không được để trống")
    BigDecimal balance;

    @NotNull(message = "Loại ví không được để trống")
    String type;

    Long userId;
}
