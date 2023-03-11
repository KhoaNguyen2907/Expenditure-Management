package com.devper.reporter.model.request;

import com.devper.reporter.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionRequest {
    private UUID id;
    private String category;
    private TransactionType type;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
}
