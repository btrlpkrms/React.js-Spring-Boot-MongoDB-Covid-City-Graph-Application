package com.tr.covid.repository;


import com.tr.covid.model.GraphData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GraphDataRepository extends MongoRepository<GraphData,String> {

}
