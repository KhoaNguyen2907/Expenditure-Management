package com.devper.tracker.service;

import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionInfo;
import com.devper.tracker.model.response.TransactionResponse;

import java.util.UUID;


public interface TransactionService {
    TransactionResponse getAll();

    TransactionInfo get(UUID id);

    void delete(UUID id);

    TransactionInfo create(CreateTransactionRequest request);

    TransactionInfo update(UUID id, UpdateTransactionRequest request);
}
