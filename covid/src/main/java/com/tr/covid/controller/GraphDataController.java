package com.tr.covid.controller;

import com.tr.covid.repository.GraphDataRepository;
import com.tr.covid.service.CovidApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/graphData")
@CrossOrigin
public class GraphDataController {


    @Autowired
    GraphDataRepository graphDataRepository;

    @Autowired
    CovidApplicationService covidApplicationService;

    @GetMapping("/{id}")
    public LinkedHashMap GetGraphData(@PathVariable String id) {
        covidApplicationService.parse();
        return  graphDataRepository.findById(id).orElse(null).getCityHashMap();
    }




}
