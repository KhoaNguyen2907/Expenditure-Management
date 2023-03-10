package com.devper.tracker.service;

import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface TransactionService {
    List<TransactionResponse> getAll();

    TransactionResponse get(UUID id);

    void delete(UUID id);

    TransactionResponse create(CreateTransactionRequest request);

    TransactionResponse update(UUID id, UpdateTransactionRequest request);
}
