package com.devper.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthReport {
    @Id
    private String id;
    private UUID ownerId;
    private int month;
    private ReportStat stats;
    private LocalDateTime lastedUpdate;
    private List<ReportTransaction> incomes;
    private List<ReportTransaction> expenses;
    private List<DayReport> dayReports;
}
