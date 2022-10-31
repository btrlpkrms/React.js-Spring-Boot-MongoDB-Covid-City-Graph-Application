package com.tr.covid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
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
