package com.emre.covid.service.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.emre.covid.model.Covid;
import com.emre.covid.model.Cities;
import com.emre.covid.repository.CitiesRepository;
import com.emre.covid.repository.CovidRepository;
import com.emre.covid.service.CovidApplicationService;
import com.emre.covid.util.FormattedDateMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CovidApplicationServiceImpl implements CovidApplicationService {

    @Autowired
    CovidRepository covidRepository;

    @Autowired
    CitiesRepository citiesRepository;

    @Autowired
    FormattedDateMatcher formattedDateMatcher;
    

    final private List<String> keywords = Arrays.asList("vaka", "vefat","taburcu");

    @Override
    public void parse() {
        
        List<Covid> textList = covidRepository.findAll();
        List<Cities> cityList = citiesRepository.findAll();
        LinkedHashMap<String,LinkedHashMap<String,Integer>> cityHashMap;
        LinkedHashMap<String,LinkedHashMap<String,Integer>> dHashMap = new LinkedHashMap<>();
        textList.forEach(text -> {
            String caseDate;
            List<String> getDates = Arrays.asList(text.getTextString().split(" "));
            List<String> getCases = Arrays.asList(text.getTextString().split("\\."));
            for(String word : getDates){
                if(word.matches("^\\d{4}\\.\\d{2}\\.\\d{2}$")){
                    caseDate = word;
                }
                else{
                    System.out.println("tarih alınamadı.");
                }
            }

            for(String case : getCases){
                List<String> getWords = Arrays.asList(case.split(" "));
                System.out.println();

            }
                
            



        });
    }

    public LinkedHashMap<String,LinkedHashMap<String,Integer>> createCityMap(List<Cities> cityList,String date, Integer caseNumber,Integer deathInteger, Integer recoveInteger) {
        LinkedHashMap<String,LinkedHashMap<String,Integer>> cityHashMap = new LinkedHashMap<>();
        LinkedHashMap dateHashMap = createDateMap(date,caseNumber,deathInteger,recoveInteger);
        cityList.forEach(city ->{
            cityHashMap.put(city.getName() , dateHashMap);
        }); 
        return cityHashMap;
    }

    public LinkedHashMap createDateMap(String date, Integer caseNumber,Integer deathInteger, Integer recoveInteger){
        LinkedHashMap dateHashMap = new LinkedHashMap<>();
        LinkedHashMap<String,Integer> caseHashmap = new LinkedHashMap<>();
        caseHashmap.put("vaka", caseNumber);
        caseHashmap.put("vefat" , deathInteger);
        caseHashmap.put("taburcu", recoveInteger);
        dateHashMap.put(date, caseHashmap);
        return dateHashMap;
    }
    public void findDate(String text){
        

    }



}