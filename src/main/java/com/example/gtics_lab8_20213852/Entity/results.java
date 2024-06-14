package com.example.gtics_lab8_20213852.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class results {
    private String original_title;
    private String overview;
    private Double popularity;
    private String release_date;
}
