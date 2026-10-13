package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Shelter {

    private String name;
    private boolean availability;
    private ArrayList<Resource> resources;
    private Location location;
    private ArrayList<ShelterType> shelterTypes;
    private int occupancy;
    private int capacity;
    private UUID id;

    public Shelter(String name, boolean availability, Location location, ArrayList<Resource> resources, ArrayList<ShelterType> shelterTypes, int occupancy, int capacity, UUID id){
        this.name = name;
        this.availability = availability;
        this.location = location;
        this.shelterTypes = shelterTypes;
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public boolean getAvailability(int occupancy, int capacity) {
        if (capacity >= occupancy) return false;
        return true;
    }

    public Location getLocation() {
        return this.location;
    }

    public ArrayList<ShelterType> getShelterType() {
        return this.shelterTypes;
    }

    public int getOccupancy() {
        return this.occupancy;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public UUID getId(){
        return this.id;
    }

    public ArrayList<Resource> getResources(){
        return resources;
    }

    public void updateAvailability(){
        if(availability)
            this.availability = false;
        else
            this.availability = true;
    }

    public void listResource(){
        for( Resource resource : resources)
            System.out.println(resource);
    }

    public void addResource(Resource resource){
        resources.add(resource);
    }
}