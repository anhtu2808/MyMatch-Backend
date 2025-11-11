package com.mymatch.dto.response.dashboard;

import java.time.LocalDateTime;

import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.enums.TransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDashboardResponse {
    Long id;
    String transactionCode;
    Long coin;
    Double amount; // VND
    TransactionType type;
    TransactionStatus status;
    TransactionSource source;
    String description;
    String userName;
    String userEmail;
    Long campusId;
    String campusName;
    Long universityId;
    String universityName;
    LocalDateTime createAt;
}
