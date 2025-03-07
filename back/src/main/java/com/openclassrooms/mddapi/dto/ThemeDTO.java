package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ThemeDTO {

    private Integer id;
    private String theme;
    private String description;
    @JsonProperty("isSubscribed")
    private boolean isSubscribed;

}
