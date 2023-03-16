package com.devper.reporter.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "day_report")
public class DayReport {
    @Id
    private String id;
    private String username;
    @Indexed
    private String date;
    private ReportStat stats;
    private String lastedUpdate;
    private List<ReportTransaction> incomes;
    private List<ReportTransaction> expenses;
}
