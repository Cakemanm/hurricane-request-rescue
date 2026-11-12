package com.model;

import java.util.ArrayList;
import java.util.UUID;
import com.location.Address;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * Represents a user and their account information, household,
 * volunteer skills, and assigned shelter.
 */
public class User {
    
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
    private String password;
    private Shelter shelter = null;
    private ArrayList<Skill> skills;
    private ArrayList<FamilyMember> familyMembers;
    private boolean hasPet;
    private int age;
    private String petDescription;

        /**
     * Creates a user with the specified volunteer skills.
     *
     * @param id the user's ID, or {@code null} to generate a new ID
     * @param username the account username
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param phoneNumber the user's phone number
     * @param address the user's address
     * @param password the account password
     * @param shelter the assigned shelter, or {@code null} if none
     * @param familyMembers the user's family members, or {@code null} for an empty list
     * @param skills the user's volunteer skills, or {@code null} for an empty list
     * @param hasPet whether the user has a pet
     * @param age the user's age
     * @param petDescription a description of the user's pet
     */
    public User(UUID id, String username, String firstName, String lastName, String phoneNumber, Address address, String password, Shelter shelter, 
    ArrayList<FamilyMember> familyMembers, ArrayList<Skill> skills, boolean hasPet, int age, String petDescription) {
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
        this.petDescription = petDescription;
    }

    /**
     * Creates a user with an empty list of volunteer skills.
     *
     * @param id the user's ID, or {@code null} to generate a new ID
     * @param username the account username
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param phoneNumber the user's phone number
     * @param address the user's address
     * @param password the account password
     * @param shelter the assigned shelter, or {@code null} if none
     * @param familyMembers the user's family members, or {@code null} for an empty list
     * @param hasPet whether the user has a pet
     * @param age the user's age
     * @param petDescription a description of the user's pet
     */
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
        this.familyMembers = (familyMembers == null ? new ArrayList<>() : familyMembers);
        this.petDescription = petDescription;
    }

    /**
     * Formats the user's name, username, phone number, and volunteer skills.
     * @return a text summary of the user's volunteer information
     */
    public String volunteerToString(){
        String skillsString = "";
        for(int i = 0; i < skills.size(); i++)
            skillsString += skills.get(i).label + (i < skills.size()-1 ? ", " : "");
        return this.getFullName() + " (@" + this.username + ")\n" + this.phoneNumber + "\nSpecialties: " + skillsString;
    }

    /**
     * Formats the user's name, username, address, family members, and pet details.
     * @return a text summary of the user's household information
     */
    public String victimToString(){
        String family = "";
        for(int i = 0; i < familyMembers.size();i++)
            family += familyMembers.get(i).getName() + (i < familyMembers.size()-1 ? ", " : "");
        return this.getFullName() + " (@" + this.username + ")\n" + address.toString() + "\nFamily Members: " + family + (hasPet ? " | " + petDescription : "");
    }

    /**
     * Checks whether the user has a pet.
     * @return {@code true} if the user has a pet; {@code false} otherwise
     */
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

    /**
     * Sets the user's assigned shelter.
     * @param sheter the shelter to assign, or {@code null} to clear the assignment
     */
    public void setShelter(Shelter sheter) {
        this.shelter = sheter;
    }
    
    /**
     * Checks whether the supplied credentials match the user's credentials.
     *
     * @param username the username to check
     * @param password the password to check
     * @return {@code true} if both credentials match; {@code false} otherwise
     */
    public boolean isMatch(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Retrieves the user's unique ID.
     * @return the user's ID
     */
    public UUID getID(){
        return this.id;
    }

    /**
     * Retrieves the account username.
     * @return the user's username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Retrieves the user's first name.
     * @return the user's first name
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Retrieves the user's last name.
     * @return the user's last name
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Combines the user's first and last names with a space.
     * @return the user's full name
     */
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    /**
     * Retrieves the user's phone number.
     * @return the user's phone number
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Retrieves the user's address.
     * @return the user's address
     */
    public Address getAddress(){
        return this.address;
    }

    /**
     * Retrieves the account password.
     * @return the user's password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Retrieves the user's assigned shelter.
     * @return the assigned shelter, or {@code null} if none
     */
    public Shelter getShelter(){
        return this.shelter;
    }

    /**
     * Retrieves the underlying list of volunteer skills.
     * @return the user's skills
     */
    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    /**
     * Retrieves the underlying list of family members.

     * @return the user's family members
     */
    public ArrayList<FamilyMember> getFamilyMembers(){
        return this.familyMembers;
    }
}