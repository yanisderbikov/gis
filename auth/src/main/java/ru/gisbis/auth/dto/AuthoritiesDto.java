package ru.gisbis.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;



public class AuthoritiesDto {
    @JsonProperty("username")
    private String userName;

    public AuthoritiesDto(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
