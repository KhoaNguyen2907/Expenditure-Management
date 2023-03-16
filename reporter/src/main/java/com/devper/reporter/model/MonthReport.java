package com.devper.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthReport {
    @Id
    private String id;
    @Indexed
    private String username;
    private int month;
    private ReportStat stats;
    private LocalDateTime lastedUpdate;
    private List<ReportTransaction> incomes;
    private List<ReportTransaction> expenses;
    private List<DayReport> dayReports;
}
