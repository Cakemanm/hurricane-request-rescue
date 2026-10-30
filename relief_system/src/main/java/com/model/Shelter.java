package com.model;
import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;

/**
 * A place where people affected by a storm can stay and recieve resources.
 * Tracks occupancy, shelter type, and available supplies
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */

public class Shelter {

    private String name;
    private boolean availability;
    private ArrayList<Resource> resources;
    private Address address;
    private ArrayList<ShelterType> shelterTypes;
    private int occupancy;
    private int capacity;
    private UUID id;
    private int petCapacity;
    private int petCount;

    /**
     * Creates a shelter with pet space
     * @param name the shelter's name
     * @param availability whether the shelter is open
     * @param resources the supplies on hand
     * @param address the shelter's address
     * @param shelterTypes what kind of shelter it is
     * @param occupancy how many people are currently staying there
     * @param capacity the max people it can hold
     * @param id the shelter's unique ID
     * @param petCapacity the max people it can hold
     * @param petCount how many pets are currently staying there
     */
    public Shelter(String name, boolean availability, ArrayList<Resource> resources, Address address, ArrayList<ShelterType> shelterTypes, int occupancy, int capacity, UUID id, int petCapacity, int petCount){
        this.name = name;
        this.resources = resources;
        this.address = address;
        this.availability = availability;
        this.shelterTypes = (shelterTypes == null ? new ArrayList<>() : shelterTypes);
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.id = id;
        this.resources = (resources == null ? new ArrayList<>() : resources);
        this.petCapacity = petCapacity;
        this.petCount = petCount;
    }

    /**
     * Creates a shelter with no pet space
     * @param name the shelter's name
     * @param availability whether the shelter is open
     * @param resources the supplies on hand
     * @param address the shelter's address
     * @param shelterTypes what kind of shelter it is
     * @param occupancy how many people are currently staying there
     * @param capacity the max people it can hold
     * @param id the shelter's unique ID
     */
    public Shelter(String name, boolean availability, ArrayList<Resource> resources, Address address, ArrayList<ShelterType> shelterTypes, int occupancy, int capacity, UUID id){
        this.name = name;
        this.resources = resources;
        this.address = address;
        this.availability = availability;
        this.shelterTypes = (shelterTypes == null ? new ArrayList<>() : shelterTypes);
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.id = id;
        this.resources = (resources == null ? new ArrayList<>() : resources);

    }

    /**
     * 
     * @param type the type to check for
     * @return true if shelter has that type
     */
    public boolean hasType(ShelterType type) {
        return shelterTypes.contains(type);
    }

    /**
     * gets how many more people the shelter can take
     * @return the number of open spaces
     */
    public int getAvailableSpaces() {
        return Math.max(0, capacity - occupancy);
    }

    /**
     * Gets how many pets a shelter can take
     * @return the number of open pet spaces
     */
    public int getAvailablePetSpaces() {
        return Math.max(0, petCapacity - petCount);
    }

    /**
     * checks group of people and pets in if there is room for all of them
     * @param people how many people are checking in
     * @param pets how many pets are checking in
     */
    public void addOccupants(int people, int pets) {
        if (getAvailableSpaces() < people || getAvailablePetSpaces() < pets) return;
        occupancy += people;
        petCount += pets;
    }

    /**
     * Returns the most pets the shelter can hold
     * @return the pet capacity
     */
    public int getPetCapacity() {
        return petCapacity;
    }

    /**
     * returns how many pets are staying in the shelter
     * @return the current number of pets
     */
    public int getPetCount() {
        return petCount;
    }

    /**
     * Returns the shelter's name
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks whether a shelter is available based on given numbers
     * @param occupancy how many people are staying there
     * @param capacity the most people it can hold
     * @return true if occupancy is below capacity
     */
    public boolean getAvailability(int occupancy, int capacity) {
        if (capacity <= occupancy) return false;
        return true;
    }

     /**
     * Checks whether the shelter has room for at least one more person
     * @return true if occupancy is below capacity
     */
    public boolean isAvailable() {
        return occupancy < capacity;
    }

    /**
     * Checks a person in if there is room
     */
    public void addOccupant() {
        if (!isAvailable()) return;
        occupancy++;
    }

    /**
     * Checks a group in if there is room for all
     * @param size how many people are in the group
     */
    public void addOccupantGroup(int size){
        if(this.getAvailableSpaces() > size)
            occupancy += size;
        else
            System.out.println("This shelter doesn't have enough availability for this group");
    }

    /**
     * checks a person out
     */
    public void removeOccupant() {
        if (occupancy > 0) occupancy--;
    }

    /**
     * returns the shelter's address
     * @return the address
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * returns what kind of shelter is is
     * @return the shelter type(s)
     */
    public ArrayList<ShelterType> getShelterTypes() {
        return this.shelterTypes;
    }

    /**
     * Returns how many people are staying in the shelter
     * @return the occupancy
     */
    public int getOccupancy() {
        return this.occupancy;
    }

    /**
     * returns the max people the shelter can hold
     * @return the capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the shelter's unique ID
     * @return the ID
     */
    public UUID getId(){
        return this.id;
    }

    /**
     * returns the supplies on hand
     * @return the resources
     */
    public ArrayList<Resource> getResources(){
        return resources;
    }

    /**
     * Adds more of a resource to any existing amount
     * @param type the kind of resource
     * @param quantity how many to add
     */
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

    /**
     * Recalculate whether the shelter is open from its occupancy
     */
    public void updateAvailability(){
        if(availability)
            this.availability = false;
        else
            this.availability = true;
    }

    /**
     * Prints every resource a shelter has
     */
    public void listResource(){
        for( Resource resource : resources)
            System.out.println(resource);
    }

    /**
     * Adds a resource to the shelter
     * @param resource the resource to add
     */
    public void addResource(Resource resource){
        resources.add(resource);
    }

    /**
     * describe the shelter, its open spots, resources, and types
     * @return a multi-line description
     */
    public String toString(){
        String returnString = "";
        returnString += this.name + "\nLocated at "+ address.toString() + " with " + (this.availability ? (capacity-occupancy) + " open spots" : "no open spots");
        returnString += "\nAvailable resources: ";
        for(Resource resource : resources)
            returnString += "\n" + resource.toString();
        returnString += "\nShelter Types: ";
        for(int i = 0; i < shelterTypes.size(); i++)
            returnString += shelterTypes.get(i) + (i < shelterTypes.size() - 1 ? ", " : "");
        return returnString;
    }
}