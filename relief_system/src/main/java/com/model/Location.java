package com.model;

import java.util.UUID;

public class Location {
    private UUID id;
    private String state;
    private String city;
    private int zipcode;
    private String address;
    private double latitude;
    private double longitude;

    /* Creates location w/ street adress & map coords
    
    @param id = location's id
    @param state = two-letter state abbreviation
    @param city = the city
    @param zipCode = the zipcode
    @param address = the street adress
    @param lat = the latitude in degrees
    @param longi = the longitude in degrrees
    */
    public Location(UUID id, String state, String city, int zipCode, String address, double lat, double longi) {
        this.id = id;
        this.state = state;
        this.city = city;
        this.zipcode = zipCode;
        this.address = address;
        this.latitude = lat;
        this.longitude = longi;
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

    public int getZicode() {
        return zipcode;
    }

    public void setZipcode(int zipCode) {
        this.zipcode = zipCode;
    }

    /* gets street address
    @return the street address */
    public String getAddress() {
        return address;
    }

    /* gets the latitude
    @param the latitude in degrees */
    public double getLatitude() {
        return latitude;
    }

    /* gets the longitude
    @param the longitude in degrees */
    public double getLongitude() {
        return longitude;
    }

    /* Two locations are equal when they have the same id
    @param obj = object to compare with
    @return true if obj is a lcoation with same id */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Location && id != null && id.equals(((Location) obj).id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public String toString(){
        return city + ", " + state + " " + zipcode;
    }
    
}
