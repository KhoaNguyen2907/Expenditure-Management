package com.devper.reporter.service;

import com.devper.reporter.model.Balance;
import com.devper.reporter.model.request.TransactionRequest;
import com.devper.reporter.model.response.DayReportResponse;

import java.time.LocalDate;

public interface ReportService {
    DayReportResponse getDayReport(LocalDate date);

    Balance getCurrentBalance();

    Balance getCurrentBalanceByToken(String token);

    void processNewTransaction(TransactionRequest transaction);

    void processUpdateTransaction(TransactionRequest transaction);
}
