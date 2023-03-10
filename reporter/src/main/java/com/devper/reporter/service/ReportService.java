package com.devper.reporter.service;

import com.devper.reporter.model.response.DayReportResponse;

import java.math.BigDecimal;

public interface ReportService {
    DayReportResponse getDayReport(int day, int month, int year);

    BigDecimal getCurrentBalance();
}
