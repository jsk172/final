package com.khit.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class NaverDTO {
    private String id;
    private String email;
    private String name;

    public NaverDTO(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public NaverDTO() {

    }
}
