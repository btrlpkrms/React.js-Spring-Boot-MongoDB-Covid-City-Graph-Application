package com.tr.covid.controller;

import com.tr.covid.model.Covid;
import com.tr.covid.repository.CovidRepository;
import com.tr.covid.service.CovidApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class CovidController {

    @Autowired
    CovidRepository covidRepository;

    @Autowired
    CovidApplicationService covidApplicationService;


    @GetMapping("/")
    public List<Covid> GetUsers() {
        return covidRepository.findAll();
    }

    @GetMapping("/{id}")
    public Covid GetUsers(@PathVariable String id) {
        return covidRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Covid postMethodName(@RequestBody Covid covid) {
        return covidRepository.save(covid);
    }

    @PutMapping("/")
    public Covid PutMapping(@RequestBody Covid newCovid) {
        Covid oldCovid = covidRepository.findById(newCovid.getId()).orElse(null);
        oldCovid.setTextString(newCovid.getTextString());
        covidRepository.save(oldCovid);
        return oldCovid;
    }

    @DeleteMapping("/{id}")
    public String DeleteUser(@PathVariable String id) {
        covidRepository.deleteById(id);
        return id;
    }





}