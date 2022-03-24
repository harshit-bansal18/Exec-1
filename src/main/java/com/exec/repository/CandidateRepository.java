package com.exec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.exec.model.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String> {

}
