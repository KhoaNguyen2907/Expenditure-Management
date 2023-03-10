package com.devper.tracker.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionRequest {
    private String category;
    private String type;
    private String description;
    private BigDecimal amount;
}
