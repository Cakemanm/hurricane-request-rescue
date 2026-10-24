package com.model;
import java.util.ArrayList;
import java.util.UUID;
import com.location.Address;
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

    public Shelter(String name, boolean availability, Address address, ArrayList<Resource> resources, ArrayList<ShelterType> shelterTypes, int occupancy, int capacity, UUID id, int petCapacity, int petCount){
        this.name = name;
        this.resources = resources;
        this.availability = availability;
        this.address = address;
        this.shelterTypes = (shelterTypes == null ? new ArrayList<>() : shelterTypes);
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.id = id;
        this.resources = (resources == null ? new ArrayList<>() : resources);
        this.petCapacity = petCapacity;
        this.petCount = petCount;
    }

        public Shelter(String name, boolean availability, Address address, ArrayList<Resource> resources, ArrayList<ShelterType> shelterTypes, int occupancy, int capacity, UUID id){
        this.name = name;
        this.resources = resources;
        this.availability = availability;
        this.address = address;
        this.shelterTypes = (shelterTypes == null ? new ArrayList<>() : shelterTypes);
        this.occupancy = occupancy;
        this.capacity = capacity;
        this.id = id;
        this.resources = (resources == null ? new ArrayList<>() : resources);

    }

    /* Checks if the shelter has a type
    
    @param type = type to check for
    @return true if shelter has that type*/
    public boolean hasType(ShelterType type) {
        return shelterTypes.contains(type);
    }

    /* Get how many people shelter can take
    
    @return the number of open spaces*/
    public int getAvailableSpaces() {
        return Math.max(0, capacity - occupancy);
    }

    /* get how many pets a shelter can take
    
    @return the number of open pet spaces*/
    public int getAvailablePetSpaces() {
        return Math.max(0, petCapacity - petCount);
    }

    /* checks group of people and pets in if there is room for all of them
    @param people = how many people are checking in
    @param pets = how many pets are checking in */
    public void addOccupants(int people, int pets) {
        if (getAvailableSpaces() < people || getAvailablePetSpaces() < pets) return;
        occupancy += people;
        petCount += pets;
    }

    public int getPetCapacity() {
        return petCapacity;
    }

    public int getPetCount() {
        return petCount;
    }

    public String getName() {
        return this.name;
    }

    public boolean getAvailability(int occupancy, int capacity) {
        if (capacity >= occupancy) return false;
        return true;
    }

    public boolean isAvailable() {
        return occupancy < capacity;
    }

    public void addOccupant() {
        if (!isAvailable()) return;
        occupancy++;
    }

    public void addOccupantGroup(int size){
        if(this.getAvailableSpaces() > size)
            occupancy += size;
        else
            System.out.println("This shelter doesn't have enough availability for this group");
    }

    public void removeOccupant() {
        if (occupancy > 0) occupancy--;
    }

    public Address getAddress() {
        return this.address;
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

    public void addResource(Resource resource){
        resources.add(resource);
    }

    public String toString(){
        String returnString = "";
        returnString += this.name + "\nLocated in " + address.toString() + " with " + (this.availability ? (capacity-occupancy) + " open spots" : "no open spots");
        returnString += "\nAvailable resources: ";
        for(Resource resource : resources)
            returnString += "\n" + resource.toString();
        returnString += "\nShelter Types: ";
        for(ShelterType type : shelterTypes)
            returnString += type;
        return returnString;
    }
}