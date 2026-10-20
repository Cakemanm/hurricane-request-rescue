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
        this.resources = resources;
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

    public boolean isAvailable() {
        return availability && occupancy < capacity;
    }

public void addOccupant() {
    if (occupancy < capacity) {
        occupancy++;
    }
}
    public Location getLocation() {
        return this.location;
    }

    public ArrayList<ShelterType> getShelterTypes() {
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
    public void restock(ResourceType type, int quantity) {
    if (resources == null) {
        resources = new ArrayList<>();
    }
    for (Resource resource : resources) {
        if (resource.getResourceType() == type) {
            resource.updateQuantity(resource.getQuantity() + quantity);
            return;
        }
    }
    resources.add(new Resource(type, quantity));
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

    public void updateSupplyLevel(){

    }

    public void addResource(Resource resource){

    }

    public String toString(){
        String returnString = "";
        returnString += this.name + "\nLocated in " + location.toString() + " with " + (this.availability ? (capacity-occupancy) + " open spots" : "no open spots");
        returnString += "\nAvailable resources: ";
        for(Resource resource : resources)
            returnString += "\n" + resource.toString();
        returnString += "\nShelter Types: ";
        for(ShelterType type : shelterTypes)
            returnString += type;
        return returnString;
    }
}