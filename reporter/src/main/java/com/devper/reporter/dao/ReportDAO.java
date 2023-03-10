package com.devper.reporter.dao;

import com.devper.reporter.model.DayReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportDAO extends MongoRepository<DayReport, String> {

}
