package com.exec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.exec.model.Penalty;

public interface PenaltyRepository extends MongoRepository<Penalty, String> {

}
