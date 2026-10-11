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

    public Shelter(String name, boolean availability, Location location, ArrayList<Resource> resources, ArrayList<ShelterType> shelterTypes, int occupancy, int capacity, UUID id){
        this.name = name;
        this.availability = availability;
        this.location = location;
        this.shelterType = shelterType;
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public ShelterType getShelterType() {
        return this.shelterType;
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

    public void updateAvailability(){
        availability = availability ? false : true;
    }

    public void listResource(){
        for( Resource resource : resources)
            System.out.println(resources);
    }

    public void updateSupplyLevel(){

    }

    public void updateEquipment(){

    }

    public void updateMedicalStaff(){

    }

    public void postAnnouncement(String announcement){

    }

    public void addResource(Resource resource){

    }
}