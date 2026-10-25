package com.model;

import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

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

    public Address[] getAddresses(ArrayList<Shelter> specificShelters){
        Address[] shelterAddresses = new Address[specificShelters.size()];
        for(int i = 0; i < specificShelters.size(); i++)
            shelterAddresses[i] = specificShelters.get(i).getAddress();
        System.out.println(shelterAddresses.length);
        return shelterAddresses;
    }

    /**
     * Finds the closest shelter to a user
     * @param user user searching
     * @param shelters to search from (used because findShelters() needs to search a specific
     * list it has already created)
     * @return closest shelter
     */
    public Shelter findClosestShelter(User user, ArrayList<Shelter>shelters){
        try{
            Address address = MapTools.getInstance().findClosestAddress(user.getAddress(),shelterList.getAddresses(shelters));
            for(Shelter shelter : shelterList.getShelters()){
                if(address.equals(shelter.getAddress())){
                    return shelter;
                }
            }

        } catch (Exception e) {
            System.out.println("There was a problem finding a shelter.");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Finds the shelters that can take a group if all parameters are met
     * and lists shelters in order of closest to farthest away from the user searching
     * @param groupSize how many people need space
     * @param needMedicalStaff whether the group needs medical space
     * @param hasPet whether the group has a pet
     * @param limitedMobility whether the group needs disability access
     * @return the shelters that meet every need
     */
    public ArrayList<Shelter> findShelters(User searcher, int groupSize, boolean needMedicalStaff, boolean hasPet, boolean limitedMobility){
        boolean match = false;
        ArrayList<Shelter> sheltersTemp = new ArrayList<>();
        ReliefFacade reliefFacade = new ReliefFacade();
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
            sheltersTemp.add(shelter);
        }

        ArrayList<Shelter> shelterMatches = new ArrayList<>();
        int e = sheltersTemp.size();
        for(int i = 0; i < e; i++){
            Shelter shelter = findClosestShelter(searcher, sheltersTemp);
            shelterMatches.add(shelter);
            sheltersTemp.remove(shelter);
        }
        return shelterMatches;
    }

    /**
     * Saves every shelter through {@link DataWriter}
     */
    public void save() {
        DataWriter.saveShelters(shelters);
    }
}