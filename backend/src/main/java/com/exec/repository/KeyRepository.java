

package com.exec.repository;

import com.exec.model.Key;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyRepository extends MongoRepository<Key, String> {

}