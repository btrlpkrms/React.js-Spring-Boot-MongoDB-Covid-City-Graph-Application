package com.emre.covid.controller;

import java.util.List;

import com.emre.covid.model.Covid;
import com.emre.covid.repository.CovidRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class CovidController {

    @Autowired
    CovidRepository covidRepository;


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
    public Covid PutMapping(@RequestBody Covid newCovid){
        Covid oldCovid = covidRepository.findById(newCovid.getId()).orElse(null);
        oldCovid.setTextString(newCovid.getTextString());
        covidRepository.save(oldCovid);
        return oldCovid;
    }

    @DeleteMapping("/{id}")
    public String DeleteUser(@PathVariable String id){
        covidRepository.deleteById(id);
        return id;
    }

}