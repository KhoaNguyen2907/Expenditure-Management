package com.devper.reporter.dao;

import com.devper.reporter.model.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceReportDAO extends MongoRepository<Balance, String> {
}
