package com.model;

import java.util.UUID;

public class Location {
    private UUID id;
    private String state;
    private String city;
    private int zipCode;

    public Location(String state, String city, int zipCode) {
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
    }
}
