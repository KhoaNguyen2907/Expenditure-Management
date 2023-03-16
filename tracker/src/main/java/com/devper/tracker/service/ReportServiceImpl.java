package com.devper.tracker.service;

import com.devper.tracker.grpc.ReportClientGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private ReportClientGrpc reporterClient;

    public ReportServiceImpl(ReportClientGrpc reporterClient) {
        this.reporterClient = reporterClient;
    }


    @Override
    public BigDecimal getCurrentBalance() {
        try {
            long balance = reporterClient.getBalance(getCurrentJwtToken()).getBalance();
            return BigDecimal.valueOf(balance) ;
        } catch (Exception e) {
            log.error("exception: {}", e);
            return BigDecimal.ZERO;
        }

    }

    public String getCurrentJwtToken() {
        return "Bearer " + SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
