package com.model;

import java.util.UUID;

public class Location {
    private UUID id;
    private String state;
    private String city;
    private int zipcode;

    public Location(UUID id, String state, String city, int zipCode) {
        this.id = id;
        this.state = state;
        this.city = city;
        this.zipcode = zipCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipcode;
    }

    public void setZipCode(int zipCode) {
        this.zipcode = zipCode;
    }

    public String toString(){
        return city + ", " + state + ", " + zipcode;
    }
    
}
