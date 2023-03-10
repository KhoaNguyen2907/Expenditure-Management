package com.devper.tracker.service;

import com.devper.common.exception.NotFoundException;
import com.devper.common.utils.ProjectMapper;
import com.devper.tracker.dao.TransactionDAO;
import com.devper.tracker.model.Transaction;
import com.devper.tracker.model.TransactionType;
import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionInfo;
import com.devper.tracker.model.response.TransactionResponse;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;
    private ProjectMapper mapper;
    private ReportService reportService;

    public TransactionServiceImpl(TransactionDAO transactionDAO, ProjectMapper mapper, ReportService reportService) {
        this.transactionDAO = transactionDAO;
        this.mapper = mapper;
        this.reportService = reportService;
    }

    @Override
    public TransactionResponse getAll() {
        List<Transaction> transactionList = transactionDAO.findAll();
        log.info("transactionList: {}", transactionList);
        List<TransactionInfo> transactionInfos = transactionList.stream().map(transaction -> mapper.map(transaction, TransactionInfo.class))
                .collect(Collectors.toList());
        BigDecimal currentBalance = reportService.getCurrentBalance();
        return TransactionResponse.builder()
                .transactionList(transactionInfos)
                .currentBalance(currentBalance)
                .build();
    }

    @Override
    public TransactionInfo get(UUID id) {
        Transaction transaction = transactionDAO.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        log.info("transaction: {}", transaction);
        return mapper.map(transaction, TransactionInfo.class);
    }

    @Override
    public void delete(UUID id) {
        transactionDAO.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        transactionDAO.deleteById(id);
        log.info("delete transaction: {}", id);
        // TODO: send message to report service
    }

    @Override
    public TransactionInfo create(CreateTransactionRequest request) {
        Transaction transaction = mapper.map(request, Transaction.class);
        if (request.getType().toLowerCase().equals("expense")) {
            transaction.setType(TransactionType.EXPENSE);
        } else {
            transaction.setType(TransactionType.INCOME);
        }
        transaction = transactionDAO.save(transaction);
        log.info("create transaction: {}", transaction);

        // TODO: send message to report service

        return mapper.map(transaction, TransactionInfo.class);
    }

    @Override
    public TransactionInfo update(UUID id, UpdateTransactionRequest request) {
        Transaction transaction = transactionDAO.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        mapper.map(request, transaction);
        if (request.getType().toLowerCase().equals("expense")) {
            transaction.setType(TransactionType.EXPENSE);
        } else {
            transaction.setType(TransactionType.INCOME);
        }
        transaction = transactionDAO.save(transaction);
        log.info("update transaction: {}", transaction);

        // TODO: send message to report service

        return mapper.map(transaction, TransactionInfo.class);
    }
}
