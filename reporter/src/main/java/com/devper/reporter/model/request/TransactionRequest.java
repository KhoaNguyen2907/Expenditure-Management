package com.devper.reporter.model.request;

import com.devper.reporter.model.TransactionType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest implements Serializable {
    private UUID id;
    private String category;
    private TransactionType type;
    private String description;
    private BigDecimal amount;
    private String date;
}
