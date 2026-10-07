package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Shelter {

    private String name;
    private boolean availability = true;
    private static ArrayList<Resource> resources;
    private Location location;
    private ShelterType shelterType;
    private int occupancy;
    private int capacity;
    private UUID id;

    public Shelter(String name, Location location, ShelterType shelterType, int occupancy, int capacity){
        this.name = name;
        this.location = location;
        this.shelterType = shelterType;
        this.occupancy = occupancy;
        this.capacity = capacity;
    }

    public void updateAvailability(){
        if(availability)
            this.availability = false;
        else
            this.availability = true;
    }

    public void listResource(){
        for( Resource resource : resources)
            System.out.println(resources);
    }

    public void updateSupplyLevel(){

    }

    public void addResource(Resource resource){

    }
}