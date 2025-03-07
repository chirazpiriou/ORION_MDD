package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String email;
    private String name;
    private String password;
    private String createdAt;
    
}
