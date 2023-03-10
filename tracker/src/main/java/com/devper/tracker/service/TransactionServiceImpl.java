package com.devper.tracker.service;

import com.devper.common.exception.NotFoundException;
import com.devper.common.utils.ProjectMapper;
import com.devper.tracker.dao.TransactionDAO;
import com.devper.tracker.model.Transaction;
import com.devper.tracker.model.TransactionType;
import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;
    private ProjectMapper mapper;

    public TransactionServiceImpl(TransactionDAO transactionDAO, ProjectMapper mapper) {
        this.transactionDAO = transactionDAO;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionResponse> getAll() {
        List<Transaction> transactionList = transactionDAO.findAll();
        log.info("transactionList: {}", transactionList);
        return transactionList.stream().map(transaction -> mapper.map(transaction, TransactionResponse.class)).collect(Collectors.toList());
    }

    @Override
    public TransactionResponse get(UUID id) {
        Transaction transaction = transactionDAO.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        log.info("transaction: {}", transaction);
        return mapper.map(transaction, TransactionResponse.class);
    }

    @Override
    public void delete(UUID id) {
        transactionDAO.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        transactionDAO.deleteById(id);
        log.info("delete transaction: {}", id);
    }

    @Override
    public TransactionResponse create(CreateTransactionRequest request) {
        Transaction transaction = mapper.map(request, Transaction.class);
        if (request.getType().toLowerCase().equals("expense")) {
            transaction.setType(TransactionType.EXPENSE);
        } else {
            transaction.setType(TransactionType.INCOME);
        }
        transaction = transactionDAO.save(transaction);
        log.info("create transaction: {}", transaction);
        return mapper.map(transaction, TransactionResponse.class);
    }

    @Override
    public TransactionResponse update(UUID id, UpdateTransactionRequest request) {
        Transaction transaction = transactionDAO.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        mapper.map(request, transaction);
        if (request.getType().toLowerCase().equals("expense")) {
            transaction.setType(TransactionType.EXPENSE);
        } else {
            transaction.setType(TransactionType.INCOME);
        }
        transaction = transactionDAO.save(transaction);
        log.info("update transaction: {}", transaction);
        return mapper.map(transaction, TransactionResponse.class);
    }
}
