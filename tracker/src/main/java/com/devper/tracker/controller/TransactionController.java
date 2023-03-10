package com.devper.tracker.controller;

import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ResponseUtils;
import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionInfo;
import com.devper.tracker.model.response.TransactionResponse;
import com.devper.tracker.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> get(@PathVariable UUID id) {
        log.info("get transaction id: {}", id);
        TransactionInfo transaction = transactionService.get(id);
        return ResponseUtils.success(transaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAll() {
        log.info("get all transactions");
        TransactionResponse transactions = transactionService.getAll();
        return ResponseUtils.success(transactions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable UUID id) {
        log.info("delete transaction id: {}", id);
        transactionService.delete(id);
        return ResponseUtils.success(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> create(@RequestBody CreateTransactionRequest request) {
        log.info("create transaction request: {}", request.toString());
        TransactionInfo transaction = transactionService.create(request);
        return ResponseUtils.success(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> update(@PathVariable UUID id, @RequestBody UpdateTransactionRequest request) {
        log.info("update transaction id: {} with new info: {}", id, request.toString());
        TransactionInfo transaction = transactionService.update(id, request);
        return ResponseUtils.success(transaction, HttpStatus.OK);
    }

}
