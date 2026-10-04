package com.model;

import java.util.UUID;

public class Location {

    private String state;
    private String city;
    private int zipcode;
    
    public Location(String state, String city, int zipcode){
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
    }
}