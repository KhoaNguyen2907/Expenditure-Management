package com.devper.reporter.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "day_report")
public class DayReport {
    @Id
    private String id;
    private UUID ownerId;
    private LocalDate date;
    private ReportStat stats;
    private LocalDateTime lastedUpdate;
    private List<ReportTransaction> incomes;
    private List<ReportTransaction> expenses;
}
