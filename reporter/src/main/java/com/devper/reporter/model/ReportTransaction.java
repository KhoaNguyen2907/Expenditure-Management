package com.devper.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportTransaction {
    @Field(("transaction_id"))
    private String transactionId;
    private String category;
    @Field("transaction_date")
    private String transactionDate;
    private BigDecimal amount;
}
