package com.devper.clients.reporter;

import com.devper.common.model.response.ResponseWrapper;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient
        (value = "REPORTER")
public interface ReporterClient {
    @GetMapping(path = "/api/v1/reports/current-balance")
    ResponseEntity<ResponseWrapper> getCurrentBalance(@RequestHeader("Authorization") String token);
}
