package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubscriptionStatusDTO {
    @JsonProperty("isSubscribed")
    private boolean isSubscribed;

}
