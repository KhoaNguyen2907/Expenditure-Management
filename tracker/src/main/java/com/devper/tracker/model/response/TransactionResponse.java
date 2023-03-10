package com.devper.tracker.model.response;

import com.devper.tracker.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private List<TransactionInfo> transactionList;
    private BigDecimal currentBalance;
}
