package com.devper.tracker.service;

import com.devper.clients.reporter.ReporterClient;
import com.devper.common.model.response.ResponseWrapper;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
public class ReportServiceImpl implements ReportService {
//    @Autowired
//    private RestTemplate restTemplate;

    private ReporterClient reporterClient;

    public ReportServiceImpl(ReporterClient reporterClient) {
        this.reporterClient = reporterClient;
    }


    @Override
    public BigDecimal getCurrentBalance() {

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", getCurrentJwtToken());
//        ResponseEntity<ResponseWrapper> response = restTemplate.exchange("http://REPORTER/api/v1/reports/current-balance",
//                HttpMethod.GET, new HttpEntity<>(headers), ResponseWrapper.class);

        try {
            ResponseEntity<ResponseWrapper> response = reporterClient.getCurrentBalance(getCurrentJwtToken());
            System.out.println(BigDecimal.valueOf((Integer) response.getBody().getContent()));
            return BigDecimal.valueOf((Integer) response.getBody().getContent()) ;
        } catch (Exception e) {
            log.error("exception: {}", e);
            return BigDecimal.ZERO;
        }

    }

    public String getCurrentJwtToken() {
        return "Bearer " + SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
