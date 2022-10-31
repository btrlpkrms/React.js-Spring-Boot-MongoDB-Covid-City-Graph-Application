package com.tr.covid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphData {

    @Id
    private String id = "1";
    private LinkedHashMap cityHashMap;
}
