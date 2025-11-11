package com.mymatch.dto.request.dashboard;

import com.mymatch.enums.TransactionSource;
import com.mymatch.enums.TransactionStatus;
import com.mymatch.enums.TransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionTableFilterRequest extends DashboardFilterRequest {
    TransactionType type;
    TransactionStatus status;
    TransactionSource source;
}
