package com.devper.reporter.service;

import com.devper.reporter.model.request.TransactionRequest;
import com.devper.reporter.model.response.DayReportResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReportService {
    DayReportResponse getDayReport(LocalDate date);

    BigDecimal getCurrentBalance();

    void processNewTransaction(TransactionRequest transaction);

    void processUpdateTransaction(TransactionRequest transaction);
}
