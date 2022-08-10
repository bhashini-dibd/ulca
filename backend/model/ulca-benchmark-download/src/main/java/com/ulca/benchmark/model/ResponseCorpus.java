package com.ulca.benchmark.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCorpus {
    JSONArray corpus ;
    int totalRecords;
    int failedRecords;
}
