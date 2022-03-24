package com.exec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.exec.model.GBM;

public interface GBMRepository extends MongoRepository<GBM, String> {

}
