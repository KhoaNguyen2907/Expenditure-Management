package com.devper.reporter.controller;

import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ResponseUtils;
import com.devper.reporter.model.response.DayReportResponse;
import com.devper.reporter.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = ReportController.PATH)
public class ReportController {
    public static final String PATH = "api/v1/reports";

    @Autowired
    private ReportService reportService;

    @GetMapping(path = "/{day}/{month}/{year}")
    public ResponseEntity<ResponseWrapper> getDayReport(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year) {
         DayReportResponse report = reportService.getDayReport(day, month, year);
        return ResponseUtils.success(report, HttpStatus.OK);
         
    }
}
