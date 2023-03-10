package com.devper.reporter.model.response;

import com.devper.reporter.model.ReportStat;
import com.devper.reporter.model.ReportTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayReportResponse {
    private LocalDate date;
    private ReportStat stats;
    private LocalDateTime lastedUpdate;
    private List<ReportTransaction> incomes;
    private List<ReportTransaction> expenses;
}
