package com.model;

import java.util.UUID;
import java.util.ArrayList;
public class User {
    
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private Location location;
    private String phoneNumber;
    private String address;
    private String password;
    private UUID shelter = null;
    private ArrayList<Skill> skills;
    private boolean hasPet = false;
    private ArrayList<String> familyMembers;

    public User(String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password, ArrayList<String> familyMembers){
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.location = location;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.password = password;
            this.familyMembers = familyMembers;
        }

    public User(UUID id, String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password, UUID shelter, 
        ArrayList<String> familyMembers){
            this.id = id;
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.location = location;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.password = password;
            this.shelter = shelter;
            this.familyMembers = familyMembers;
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

    public Location getLocation(){
        return this.location;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getAddress(){
        return this.address;
    }

    public String getPassword(){
        return this.password;
    }

    public UUID getShelter(){
        return this.shelter;
    }

    public ArrayList<Skill> getSkills(){
        return this.skills;
    }

    public boolean hasPet(){
        return hasPet;
    }

    public ArrayList<String> getFamilyMembers(){
        return this.familyMembers;
    }
}
