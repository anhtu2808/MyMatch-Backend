package com.mymatch.dto.response.wallet;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletResponse {
    Long id;
    Long coin;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
