package com.devper.tracker.model.response;

import com.devper.tracker.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private UUID id;
    private String userId;
    private String category;
    private TransactionType type;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
}
