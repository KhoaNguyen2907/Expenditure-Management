package com.devper.tracker.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    private String category;
    private String type;
    private String description;
    private BigDecimal amount;
}
