package com.exec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.exec.model.AspiringCandidate;

public interface AspiringCandidateRepository extends MongoRepository<AspiringCandidate, String> {

}