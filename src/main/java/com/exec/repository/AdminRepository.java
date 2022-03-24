package com.exec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.exec.model.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {

}
