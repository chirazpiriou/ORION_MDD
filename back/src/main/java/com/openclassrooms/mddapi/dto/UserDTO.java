package com.openclassrooms.mddapi.dto;

import java.security.Timestamp;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String name;
    private Timestamp created_at;

}
