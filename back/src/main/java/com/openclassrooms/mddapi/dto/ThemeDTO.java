package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ThemeDTO {
    private Integer id;
    private String theme;
    private String description;

    // Added @JsonProperty(\"isSubscribed\") annotation to the isSubscribed field to
    // ensure that the corresponding JSON key remains 'isSubscribed' instead of 'subscribed'.
    // Before this change, Postman interpreted the property incorrectly, potentially
    // causing inconsistencies in requests."

    @JsonProperty("isSubscribed")
    private boolean isSubscribed;

}
