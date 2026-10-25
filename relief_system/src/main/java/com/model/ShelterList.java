package com.model;

import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;

public class ShelterList {

private static ShelterList shelterList;
private ArrayList<Shelter> shelters;


private ShelterList(){
    shelters = DataLoader.getShelters();
}

public static ShelterList getInstance() {
    if(shelterList == null) {
        shelterList = new ShelterList();
    }
    return shelterList;
}

public void addShelter(String name, boolean availability, Address address, ArrayList<Resource> resources, 
    ArrayList<ShelterType> type, int occupancy, int capacity, UUID id) {
    Shelter shelter = new Shelter(name, availability, resources, address, type, occupancy, capacity, (id == null ? UUID.randomUUID() : id));
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

public Address[] getAddresses(){
    Address[] shelterAddresses = new Address[shelters.size()];
    for(int i = 0; i < shelters.size(); i++)
        shelterAddresses[i] = shelters.get(i).getAddress();
    return shelterAddresses;
}

public ArrayList<Shelter> findShelters(int groupSize, boolean needMedicalStaff, boolean hasPet, boolean limitedMobility){
    boolean match = false;
    ArrayList<Shelter> shelterMatches = new ArrayList<>();
    for(Shelter shelter : shelters){
        if(groupSize > shelter.getCapacity() - shelter.getOccupancy())
            continue;
        boolean hasMedics = false;
        boolean petFriendly = false;
        boolean accessible = false;
        for(ShelterType type : shelter.getShelterTypes()){
            if(type == ShelterType.MEDICAL_STAFF)
                hasMedics = true;
            if(type == ShelterType.PET_FRIENDLY)
                petFriendly = true;
            if(type == ShelterType.DISABILITY_ACCESSIBLE)
                accessible = true;
        }
        if(needMedicalStaff && !hasMedics)
            continue;
        if(hasPet && !petFriendly)
            continue;
        if(limitedMobility && !accessible)
            continue;

        match = true;
        shelterMatches.add(shelter);
    }

    ArrayList<Shelter> sheltersTemp = shelterMatches;
    /*for(int i = 0; i < sheltersTemp.size()){
        
    }*/
    return shelterMatches;
}

    public void save() {
        DataWriter.saveShelters(shelters);
    }
}