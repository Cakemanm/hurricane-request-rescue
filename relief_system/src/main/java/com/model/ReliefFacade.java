package com.model;

import java.util.ArrayList;
import java.util.Optional;

public class ReliefFacade{
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

    public Hurricane getHurricane(){
        return hurricane;
    }

    public boolean login(String username, String password) {
        UserList userList = UserList.getInstance();
        User user = userList.getUser(username, password);
        if (user!=null){
            currentUser = user;
            return true;
        }
        else
            return false;
    }

    public void createAccount(String username, String firstName,
        String lastName, Location location, String phoneNumber,
    String address, String password, Shelter shelter, ArrayList<String> familyMembers){
            UserList userList = UserList.getInstance();
            userList.addUser(null,username,firstName, lastName, location, phoneNumber, address, password, shelter, familyMembers);
            login(username, password);
       
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logout successful");
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public ArrayList<Request> searchRequests(String key){
        System.out.println("Search results for: " + key);
        RequestFeed feed = RequestFeed.getInstance();
        ArrayList<Request> requests = feed.searchRequests(key);
        for(Request request: requests)
            System.out.println(request.toString() + "\n");
        return requests;
    }
}