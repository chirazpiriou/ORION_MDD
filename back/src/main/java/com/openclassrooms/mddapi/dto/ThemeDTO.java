package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ThemeDTO {
    private Integer id;
    private String theme;
    private String description;
    private boolean isSubscribed;

}
