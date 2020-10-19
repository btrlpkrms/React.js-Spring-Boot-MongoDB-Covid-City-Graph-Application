package com.emre.covid.repository;

import com.emre.covid.model.Cities;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends MongoRepository<Cities,String> {
    
}
