package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private Location location;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private UUID shelterID;
    private ArrayList<Skill> skills;
    private boolean hasPet;
    private ArrayList<String> familyMembers;

    public User(String username, String password) {
        
    }
    
    //needs more parameters
    public User(String username, String password, String firstName,
        String lastName, Location location, String phoneNumber, String address, UUID shelterID,
        ArrayList<Skill> skills, boolean hasPet, ArrayList<String> familyMembers, UUID id) {

    }

    public void updateProfile() {

    }

    public void downloadOffilineMap() {

    }

    public boolean isMatch(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
