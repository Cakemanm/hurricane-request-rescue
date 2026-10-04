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

    public User(String username, String password){

    }

    public User(String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password){
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.location = location;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.password = password;
        }

    public void updateProfile(){

    }

    public void downloadOfflineMap(){

    }

    public void sendMessage(String recipient, String message){

    }

    public boolean isMatch(String username, String password){
        
    }
}
