package com.devper.tracker.service;

import com.devper.common.model.response.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BigDecimal getCurrentBalance() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getCurrentJwtToken());
        ResponseEntity<ResponseWrapper> response = restTemplate.exchange("http://REPORTER/api/v1/reports/current-balance",
                HttpMethod.GET, new HttpEntity<>(headers), ResponseWrapper.class);
        log.info("response: {}", response);
        return BigDecimal.valueOf((Integer) response.getBody().getContent()) ;
    }

    public String getCurrentJwtToken() {
        return "Bearer " + SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
