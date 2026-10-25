package com.model;

import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;

/**
 * Singleton that holds every shelter in the system and searchs through them by
 * name, ID, and need of a group
 * @author Gracie Bergman
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */

public class ShelterList {

    private static ShelterList shelterList;
    private ArrayList<Shelter> shelters;

    /**
     * Loads the hselters through {@link DataLoader}
     */
    private ShelterList(){
        shelters = DataLoader.getShelters();
    }

    /**
     * returns shared shelter list, creating it on first access
     * @return the singleton shelter lsit
     */
    public static ShelterList getInstance() {
        if(shelterList == null) {
            shelterList = new ShelterList();
        }
        return shelterList;
    }

    /**
     * Creates a shelter and adds it to the list
     * @param name the shelter's name
     * @param availability whether the shelter is open
     * @param address the shelter's address
     * @param resources the supplies on hand
     * @param type what kind of shelter it is  and what it offers
     * @param occupancy how many people are staying there
     * @param capacity the most people it can hold
     * @param id the shelter's ID
     */
    public void addShelter(String name, boolean availability, Address address, ArrayList<Resource> resources, 
        ArrayList<ShelterType> type, int occupancy, int capacity, UUID id) {
        Shelter shelter = new Shelter(name, availability, resources, address, type, occupancy, capacity, (id == null ? UUID.randomUUID() : id));
        shelters.add(shelter);
    }

    /**
     * finds a shelter by name
     * @param name the shelter's name
     * @return the matching shelter or null if none is found
     */
    public Shelter getShelter(String name) {
        for (Shelter shelter : shelters) {
            if(name.equalsIgnoreCase(shelter.getName())){
                return shelter;
            }
        }
        return null;
    }

    /**
     * Finds a shelter by ID
     * @param id the shelter's ID
     * @return the matching shelter or null if none
     */
    public Shelter getShelterById(UUID id){
        for (Shelter shelter : shelters){
            if(shelter.getId().equals(id))
                return shelter;
        }
        return null;
    }

    /**
     * Returns every shelter
     * @return the list of shelters
     */
    public ArrayList<Shelter> getShelters() {
        return shelters;
    }

    /**
     * returns every shelter's address
     * @return the shelter's addresses
     */
    public Address[] getAddresses(){
        Address[] shelterAddresses = new Address[shelters.size()];
        for(int i = 0; i < shelters.size(); i++)
            shelterAddresses[i] = shelters.get(i).getAddress();
        return shelterAddresses;
    }

    /**
     * Finds the shelters that can take a group if all parameters are met
     * @param groupSize how many people need space
     * @param needMedicalStaff whether the group needs medical space
     * @param hasPet whether the group has a pet
     * @param limitedMobility whether the group needs disability access
     * @return the shelters that meet every need
     */
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

    /**
     * Saves every shelter through {@link DataWriter}
     */
    public void save() {
        DataWriter.saveShelters(shelters);
    }
}