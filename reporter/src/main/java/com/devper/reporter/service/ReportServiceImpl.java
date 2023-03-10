package com.devper.reporter.service;

import com.devper.common.utils.ProjectMapper;
import com.devper.reporter.dao.ReportDAO;
import com.devper.reporter.model.DayReport;
import com.devper.reporter.model.ReportStat;
import com.devper.reporter.model.response.DayReportResponse;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportDAO reportDAO;
    private ProjectMapper mapper;

    public ReportServiceImpl(ReportDAO reportDAO, ProjectMapper mapper) {
        this.reportDAO = reportDAO;
        this.mapper = mapper;
    }

    @Override
    public DayReportResponse getDayReport(int day, int month, int year) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UUID ownerId = (UUID) authentication.getPrincipal();
        UUID ownerId = UUID.fromString("f1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d");
        DayReport report = getDayReportByOwnerId(LocalDate.of(year, month, day), ownerId);
        DayReportResponse response = mapper.map(report, DayReportResponse.class);

        return response;
    }

    public DayReport getDayReportByOwnerId(LocalDate date, UUID ownerId) {
        DayReport dayReportProbe = new DayReport();
        dayReportProbe.setDate(date);
        dayReportProbe.setOwnerId(ownerId);
        dayReportProbe.setStats(new ReportStat());
        Optional<DayReport> optionalReport = reportDAO.findOne(Example.of(dayReportProbe));

        if (optionalReport.isPresent()) {
            return optionalReport.get();
        } else {
            dayReportProbe.getStats().setTotal(BigDecimal.valueOf(0));
            dayReportProbe.getStats().setTotalIncomes(BigDecimal.valueOf(0));
            dayReportProbe.getStats().setTotalExpenses(BigDecimal.valueOf(0));
            return dayReportProbe;
        }
    }
}
