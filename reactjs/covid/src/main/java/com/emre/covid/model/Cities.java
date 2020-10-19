package com.emre.covid.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cities {

    @Id
    private String id;
    private String name;
    private String latitude;
    private String longtitude;
    private String population;
    private String region;
}
