package com.model;

import java.util.ArrayList;

public class ReliefFacade {
    private Hurricane hurricane;
    private User currentUser;

    public ReliefFacade() {
        // TODO: implement later
    }

    public void submitRequest(User requester, Request request,
            Location loc, String comments) {
        // TODO: implement later
    }

    public Shelter signUpVictimForShelter(User user) {
        // TODO: implement later
        return null;
    }

    public void dispatchVolunteer(User user) {
        // TODO: implement later
    }

    public void restockShelter(User user) {
        // TODO: implement later
    }

    public Hurricane getStormStatus() {
        // TODO: implement later
        return null;
    }

    public RequestFeed browseRequests() {
        // TODO: implement later
        return null;
    }

    public void login(String username, String password) {
        UserList userList = UserList.getInstance();
        User user = userList.getUser(username, password);
        if (user!=null){
            currentUser = user;
            System.out.println("Login successful");
        }
    }

    public void createAccount(String username, String firstName,
        String lastName, Location location, String phoneNumber,
        String address, String password, ArrayList<String> familyMembers){
        UserList userList = UserList.getInstance();
        userList.addUser(username,firstName, lastName, location, phoneNumber, address, password, familyMembers);
        login(username, password);
       
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logout successful");
    }

    public User getCurrentUser(){
        return currentUser;
    }
}