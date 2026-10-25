package com.model;

import java.util.ArrayList;
import java.util.UUID;
import com.location.Address;

public class User {
    
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private Location location;
    private String phoneNumber;
    private Address address;
    private String password;
    private Shelter shelter = null;
    private ArrayList<Skill> skills;
    private ArrayList<FamilyMember> familyMembers;
    private boolean hasPet;
    private int age;
    private String petDescription;

    public User(UUID id, String username, String firstName, String lastName, String phoneNumber, Address address, String password, Shelter shelter, ArrayList<FamilyMember> familyMembers, boolean hasPet, int age, String petDescription) {
            this.id = (id == null ? UUID.randomUUID() : id);
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.password = password;
            this.shelter = shelter;
            this.skills = (skills == null ? new ArrayList<>() : skills);
            this.hasPet = hasPet;
            this.petDescription = petDescription;
            this.familyMembers = (familyMembers == null ? new ArrayList<>() : familyMembers);
        }

    public User(UUID id, String username, String firstName, String lastName, String phoneNumber, Address address, String password, Shelter shelter, 
    ArrayList<FamilyMember> familyMembers, ArrayList<Skill> skills, boolean hasPet, int age) {
        this.id = (id == null ? UUID.randomUUID() : id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.shelter = shelter;
        this.skills = (skills == null ? new ArrayList<>() : skills);
        this.hasPet = hasPet;
        this.familyMembers = (familyMembers == null ? new ArrayList<>() : familyMembers);
    }

    public String volunteerToString(){
        String skillsString = "";
        for(int i = 0; i < skills.size(); i++)
            skillsString += skills.get(i).label + (i < skills.size()-1 ? ", " : "");
        return this.getFullName() + " (@" + this.username + ")\n" + this.phoneNumber + "\nSpecialties: " + skillsString;
    }

    public String victimToString(){
        String family = "";
        for(int i = 0; i < familyMembers.size();i++)
            family += familyMembers.get(i).getName() + (i < familyMembers.size()-1 ? ", " : "");
        return this.getFullName() + " (@" + this.username + ")\n" + address.toString() + "Family Members: " + family + (hasPet ? " | " + petDescription : "");
    }

    public boolean hasPet() {
        return hasPet;
    }

    /* Get user's age
    @return user's age */
    public int getAge() {
        return age;
    }

    /* Describe user's pet with a text description
    @return pet description */
    public String getPetDescription() {
        return petDescription;
    }

    /* Counts number of people in house, family + self
    @return number of people */
    public int getHouseholdSize() {
        return familyMembers.size() + 1;
    }

    public void setShelter(Shelter sheter) {
        this.shelter = sheter;
    }
    
    public boolean isMatch(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    public UUID getID(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public Location getLocation(){
        return this.location;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public Address getAddress(){
        return this.address;
    }

    public String getPassword(){
        return this.password;
    }

    public Shelter getShelter(){
        return this.shelter;
    }

    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    public ArrayList<FamilyMember> getFamilyMembers(){
        return this.familyMembers;
    }
}