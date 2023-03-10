package com.devper.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportStat {
    private BigDecimal total;
    private BigDecimal totalExpenses;
    private BigDecimal totalIncomes;
}
