package com.exec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.exec.model.Report;

public interface ReportRepository extends MongoRepository<Report, String> {

}
