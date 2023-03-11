package com.devper.reporter.service;

import com.devper.common.exception.NotFoundException;
import com.devper.common.utils.ProjectMapper;
import com.devper.reporter.dao.BalanceReportDAO;
import com.devper.reporter.dao.ReportDAO;
import com.devper.reporter.model.*;
import com.devper.reporter.model.request.TransactionRequest;
import com.devper.reporter.model.response.DayReportResponse;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportDAO reportDAO;
    private ProjectMapper mapper;
    private BalanceReportDAO balanceReportDAO;

    public ReportServiceImpl(ReportDAO reportDAO, ProjectMapper mapper, BalanceReportDAO balanceReportDAO) {
        this.reportDAO = reportDAO;
        this.mapper = mapper;
        this.balanceReportDAO = balanceReportDAO;
    }

    @Override
    public DayReportResponse getDayReport(LocalDate date) {
        String username = getLoggedUsername();

        DayReport report = getDayReportByUsername(date, username);
        DayReportResponse response = mapper.map(report, DayReportResponse.class);

        return response;
    }

    private String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    @Override
    public BigDecimal getCurrentBalance() {
        //TODO: Need to update this method
        return BigDecimal.valueOf(1000);
    }

    @Override
    public void processNewTransaction(TransactionRequest transaction) {
        String username = getLoggedUsername();
        DayReport report = getDayReportByUsername(transaction.getDate().toLocalDate(), username);
        if (transaction.getType() == TransactionType.INCOME) {
            report.getStats().setTotalIncomes(report.getStats().getTotalIncomes().add(transaction.getAmount()));
            report.getIncomes().add(mapper.map(transaction, ReportTransaction.class));

            Balance currentBalance = balanceReportDAO.findById(username).orElse(null);
            if (currentBalance == null) {
                currentBalance = new Balance();
                currentBalance.setUsername(username);
                currentBalance.setBalance(transaction.getAmount());
            } else {
                currentBalance.setBalance(currentBalance.getBalance().add(transaction.getAmount()));
            }
        } else {
            report.getStats().setTotalExpenses(report.getStats().getTotalExpenses().add(transaction.getAmount()));
            report.getExpenses().add(mapper.map(transaction, ReportTransaction.class));

            Balance currentBalance = balanceReportDAO.findById(username).orElse(null);
            if (currentBalance == null) {
                currentBalance = new Balance();
                currentBalance.setUsername(username);
                currentBalance.setBalance(transaction.getAmount().negate());
            } else {
                currentBalance.setBalance(currentBalance.getBalance().subtract(transaction.getAmount()));
            }
        }
        report.getStats().setTotal(report.getStats().getTotalIncomes().subtract(report.getStats().getTotalExpenses()));
        report.setLastedUpdate(LocalDateTime.now());

        reportDAO.save(report);
    }

    @Override
    public void processUpdateTransaction(TransactionRequest transaction) {
        String username = getLoggedUsername();
        DayReport report = getDayReportByUsername(transaction.getDate().toLocalDate(), username);
        if (transaction.getType() == TransactionType.INCOME) {
            ReportTransaction oldTransaction = report.getIncomes().stream().filter(t -> t.getTransactionId().equals(transaction.getId())).findFirst().orElse(null);
            if (oldTransaction == null) throw new NotFoundException("Transaction not found");
            report.getStats().setTotalIncomes(report.getStats().getTotalIncomes().subtract(oldTransaction.getAmount()).add(transaction.getAmount()));
            report.getIncomes().remove(oldTransaction);
            report.getIncomes().add(mapper.map(transaction, ReportTransaction.class));
        } else {
            ReportTransaction oldTransaction = report.getExpenses().stream().filter(t -> t.getTransactionId().equals(transaction.getId())).findFirst().orElse(null);
            if (oldTransaction == null) throw new NotFoundException("Transaction not found");
            report.getStats().setTotalExpenses(report.getStats().getTotalExpenses().subtract(oldTransaction.getAmount()).add(transaction.getAmount()));
            report.getExpenses().remove(oldTransaction);
            report.getExpenses().add(mapper.map(transaction, ReportTransaction.class));
        }
        report.getStats().setTotal(report.getStats().getTotalIncomes().subtract(report.getStats().getTotalExpenses()));
        report.setLastedUpdate(LocalDateTime.now());

        reportDAO.save(report);

    }


    public DayReport getDayReportByUsername(LocalDate date, String username) {
        DayReport dayReportProbe = new DayReport();
        dayReportProbe.setDate(date);
        dayReportProbe.setUsername(username);
        dayReportProbe.setStats(new ReportStat());

        Optional<DayReport> optionalReport = reportDAO.findOne(Example.of(dayReportProbe));

        if (optionalReport.isPresent()) {
            return optionalReport.get();
        } else {
            dayReportProbe.getStats().setTotal(BigDecimal.valueOf(0));
            dayReportProbe.getStats().setTotalIncomes(BigDecimal.valueOf(0));
            dayReportProbe.getStats().setTotalExpenses(BigDecimal.valueOf(0));
            dayReportProbe.setLastedUpdate(LocalDateTime.now());
            dayReportProbe.setIncomes(new LinkedList<>());
            dayReportProbe.setExpenses(new LinkedList<>());
            return dayReportProbe;
        }
    }
}
