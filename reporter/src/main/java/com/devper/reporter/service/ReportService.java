package com.devper.reporter.service;

import com.devper.reporter.model.response.DayReportResponse;

public interface ReportService {
    DayReportResponse getDayReport(int day, int month, int year);
}
