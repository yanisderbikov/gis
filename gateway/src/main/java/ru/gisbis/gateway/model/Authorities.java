package ru.gisbis.gateway.model;


import com.fasterxml.jackson.annotation.JsonProperty;


public class Authorities {

    @JsonProperty("username")
    private String userName;

    public Authorities(){}

    public Authorities(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
