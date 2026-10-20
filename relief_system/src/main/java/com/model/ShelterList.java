package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class ShelterList {

private static ShelterList shelterList;
private ArrayList<Shelter> shelters = DataLoader.getShelters();


private ShelterList(){
    shelters = DataLoader.getShelters();
}

public static ShelterList getInstance() {
    if(shelterList == null) {
        shelterList = new ShelterList();
    }
    return shelterList;
}

public void addShelter(String name, boolean availability, Location location, ArrayList<Resource> resources, 
    ArrayList<ShelterType> type, int occupancy, int capacity, UUID id) {
    Shelter shelter = new Shelter(name, availability, location, resources, type, occupancy, capacity, (id == null ? UUID.randomUUID() : id));
    shelters.add(shelter);
}

public Shelter getShelter(String name) {
    for (Shelter shelter : shelters) {
        if(name.equalsIgnoreCase(shelter.getName())){
            return shelter;
        }
    }
    return null;
}

public Shelter getShelterById(UUID id){
    for (Shelter shelter : shelters){
        if(shelter.getId().equals(id))
            return shelter;
    }
    return null;
}

public ArrayList<Shelter> getShelters() {
    return shelters;
}
}