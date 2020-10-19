package com.emre.covid.repository;


import com.emre.covid.model.GraphData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GraphDataRepository extends MongoRepository<GraphData,String> {

}
