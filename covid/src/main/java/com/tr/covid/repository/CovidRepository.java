package com.tr.covid.repository;

import com.tr.covid.model.Covid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidRepository extends MongoRepository<Covid, String> {


}