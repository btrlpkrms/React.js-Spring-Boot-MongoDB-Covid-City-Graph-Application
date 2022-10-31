package com.emre.covid.service.impl;

import com.emre.covid.controller.CovidController;
import com.emre.covid.controller.GraphDataController;
import com.emre.covid.model.Cities;
import com.emre.covid.model.Covid;
import com.emre.covid.model.GraphData;
import com.emre.covid.repository.CitiesRepository;
import com.emre.covid.repository.CovidRepository;
import com.emre.covid.repository.GraphDataRepository;
import com.emre.covid.service.CovidApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CovidApplicationServiceImpl implements CovidApplicationService {

    @Autowired
    CovidRepository covidRepository;

    @Autowired
    CitiesRepository citiesRepository;


    @Autowired
    GraphDataRepository graphDataRepository;

    final private List<String> keywords = Arrays.asList("vaka", "vefat", "taburcu");


    @Override
    public void parse() {
        List<Covid> textList = covidRepository.findAll();
        List<Cities> cityList = citiesRepository.findAll();
        LinkedHashMap dates = new LinkedHashMap();
        LinkedHashMap cityHashMap = new LinkedHashMap();
        textList.forEach(text -> {
            String caseDate = null;
            int death = 0;
            int recover = 0;
            int newCase = 0;
            String cityName = null;
            List<String> getDates = Arrays.asList(text.getTextString().split(" "));
            String newText = text.getTextString().replaceAll("\\d{2}(\\.|-)\\d{2}(\\.|-)\\d{4}", "");
            List<String> getCases = Arrays.asList(newText.split("\\."));
            for (String word : getDates) {
                if (word.matches("\\d{2}(\\.|-)\\d{2}(\\.|-)\\d{4}")) {
                    caseDate = word.replaceAll("\\.", "-");
                    dates.putIfAbsent(caseDate,new LinkedHashMap<>());
                }
            }
            for (String cases : getCases) {
                List<String> getWords = Arrays.asList(cases.split(" "));
                Integer num = findNum(getWords);
                for (String words : getWords) {
                    if (keywords.contains(words)) {
                        if (words.equals("vaka")) {
                            newCase = num;
                            if(((LinkedHashMap) dates.get(caseDate)).containsKey("vaka")){
                                ((LinkedHashMap) dates.get(caseDate)).put("vaka",newCase +
                                        Integer.parseInt(((LinkedHashMap) dates.get(caseDate)).get("vaka").toString()));
                            }else{
                                ((LinkedHashMap) dates.get(caseDate)).put("vaka",newCase);
                            }
                        }
                        if (words.equals("vefat")) {
                            death = num;
                            if(((LinkedHashMap) dates.get(caseDate)).containsKey("vefat")){
                                ((LinkedHashMap) dates.get(caseDate)).put("vefat",death +
                                        Integer.parseInt(((LinkedHashMap) dates.get(caseDate)).get("vefat").toString()));
                            }else{
                                ((LinkedHashMap) dates.get(caseDate)).put("vefat",death);
                            }
                        }
                        if (words.equals("taburcu")) {
                            recover = num;
                            if(((LinkedHashMap) dates.get(caseDate)).containsKey("taburcu")){

                                ((LinkedHashMap) dates.get(caseDate)).put("taburcu",recover +
                                        Integer.parseInt(((LinkedHashMap) dates.get(caseDate)).get("taburcu").toString()));
                            }else{
                                ((LinkedHashMap) dates.get(caseDate)).put("taburcu",recover);
                            }
                        }
                    }
                    if (checkContains(cityList, words)) {
                        cityName = words;
                    }
                }
            }
            if (cityName != null) {
                if (cityHashMap.keySet().contains(cityName)) {
                    if (((LinkedHashMap) cityHashMap.get(cityName)).keySet().contains(caseDate)) {
                        ((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).replace("vaka",
                                ((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).get("vaka"),
                                Integer.parseInt(((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).get("vaka").toString()) + newCase);
                        ((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).replace("vefat",
                                ((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).get("vefat"),
                                Integer.parseInt(((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).get("vefat").toString()) + newCase);
                        ((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).replace("taburcu",
                                ((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).get("taburcu"),
                                Integer.parseInt(((LinkedHashMap) ((LinkedHashMap) cityHashMap.get(cityName)).get(caseDate)).get("taburcu").toString()) + newCase);
                    } else {
                        LinkedHashMap dHashMap = dateMap(caseDate, newCase, death, recover);
                        ((LinkedHashMap) cityHashMap.get(cityName)).put(caseDate, dHashMap);
                    }

                } else {
                    LinkedHashMap dHashMap = createDateMap(caseDate, newCase, death, recover);
                    cityHashMap.put(cityName, dHashMap);
                    Map sortedMap = new TreeMap((LinkedHashMap) cityHashMap.get(cityName));
                    LinkedHashMap sortedTreeMap = new LinkedHashMap(sortedMap);
                    cityHashMap.put(cityName,sortedTreeMap);
                }
            }


        });

        GraphData graphData = new GraphData();
        Map sortedMap = new TreeMap(dates);
        LinkedHashMap finalDates = new LinkedHashMap(sortedMap);
        cityHashMap.put("Tüm Şehirler", finalDates);
        graphData.setCityHashMap(cityHashMap);
        graphDataRepository.save(graphData);
    }

    public LinkedHashMap createDateMap(String date, Integer caseNumber, Integer deathInteger, Integer recoveInteger) {

        LinkedHashMap dateHashMap = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> caseHashmap = new LinkedHashMap<>();

        if (!dateHashMap.keySet().contains(date)) {
            caseHashmap.put("vaka", caseNumber);
            caseHashmap.put("vefat", deathInteger);
            caseHashmap.put("taburcu", recoveInteger);
        } else {

            caseHashmap.put("vaka", caseNumber);
            caseHashmap.put("vefat", deathInteger);
            caseHashmap.put("taburcu", recoveInteger);

        }
        dateHashMap.put(date, caseHashmap);
        return dateHashMap;
    }

    public LinkedHashMap dateMap(String date, Integer caseNumber, Integer deathInteger, Integer recoveInteger) {

        LinkedHashMap<String, Integer> caseHashmap = new LinkedHashMap<>();

        caseHashmap.put("vaka", caseNumber);
        caseHashmap.put("vefat", deathInteger);
        caseHashmap.put("taburcu", recoveInteger);

        return caseHashmap;
    }

    public Integer findNum(List<String> text) {
        for (String element : text) {
            char[] chars = element.toCharArray();
            for (char c : chars) {
                if (Character.isDigit(c) && !element.equals("")) {
                    System.out.println();
                    return Integer.parseInt(element);
                }
            }
        }
        return null;
    }

    public Boolean checkContains(List<Cities> cityList, String word) {
        for (Cities object : cityList) {
            if (object.getName().equals(word.trim())) {
                return true;
            }
        }
        return false;
    }



}