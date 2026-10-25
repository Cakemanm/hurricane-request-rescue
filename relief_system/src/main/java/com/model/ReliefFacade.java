package com.model;
import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.location.Address;

public class ReliefFacade {
    private ArrayList<Hurricane> hurricane;
    private User currentUser;
    private RequestFeed feed;
    private ShelterList shelterList = ShelterList.getInstance();

    public static final double NEARBY_MILES = 10.0;

    public ReliefFacade() {
        hurricane = DataLoader.getHurricanes();
        feed = RequestFeed.getInstance();
        currentUser = null;
    }

    // public void submitRequest(Request request) {
    //     feed.addRequest(request);
    // }


    public void submitRequest(User requester, Request request, String comments) {
        request.setRequester(requester);
        request.addComment(comments);
        feed.addRequest(request);
    }

    public void browseShelters(){
        System.out.println("Recommended Shelters: ");
        for(Shelter shelter : shelterList.getShelters()){
            System.out.println(shelter.toString() + "\n");
        }
    }

    public Shelter signUpVictimForShelter(User user) {
    ArrayList<Shelter> shelters = ShelterList.getInstance().getShelters();
        for (Shelter shelter : shelters) {
            if (shelter.isAvailable() && shelter.getOccupancy() < shelter.getCapacity()) {
                shelter.addOccupant();
                user.setShelter(shelterList.getShelterById(shelter.getId()));
                shelterList.save();
                UserList.getInstance().save();
                return shelter;
            }
        }
        return null;
    }

    public ArrayList<Shelter> getShelters() {
        return ShelterList.getInstance().getShelters();
    }


    // public Shelter signUpVictimForShelter(User user) {
    // ArrayList<Shelter> shelters = ShelterList.getInstance().getShelters();
    // for (Shelter shelter : shelters) {
    //     if (shelter.isAvailable() && shelter.getOccupancy() < shelter.getCapacity()) {
    //         shelter.addOccupant();
    //         user.setShelter(shelter.getId());
    //         return shelter;
    //     }
    // }
    // return null;
    // }

    public Shelter signUpForShelter(String shelterName) {
        if (currentUser == null) return null;
        Shelter shelter = ShelterList.getInstance().getShelter(shelterName);
        if (shelter == null) return null;
        if (shelter == currentUser.getShelter()) return shelter;
        if (currentUser.getShelter() != null) {
            currentUser.getShelter().removeOccupant();
        }
        shelter.addOccupant();
        currentUser.setShelter(shelter);
        shelterList.save();
        UserList.getInstance().save();
        return shelter;
    }




    public boolean dispatchVolunteer(Request request, User user) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.updateStatus(RequestStatus.HELP_ON_THE_WAY, user);
            return true;
        }
        return false;
    }

    public void restockShelter(Shelter shelter, ResourceType type, int quantity) {
    if (shelter != null) {
        shelter.restock(type, quantity);
    }
    }

    public ArrayList<Hurricane> getStormStatus() {
        return hurricane;
    }

    public RequestFeed browseRequests() {
        return feed;

    }


    public void createAccount(String username, String firstName, String lastName, String phoneNumber, Address address, String password,
        ArrayList<FamilyMember> familyMembers,boolean hasPet, int age, String petDescription){
            UserList userList = UserList.getInstance();
            if (userList.getUser(username) != null) {
                System.out.println("That username is already taken");
                return;
            }
            userList.addUser(new User(UUID.randomUUID(), username, firstName, lastName, phoneNumber, address, password, null, familyMembers, hasPet, age, petDescription));
            login(username, password);
    }


    public User createAccount(String username, String firstName, String lastName, String phoneNumber, Address address, String password, Shelter shelter,
        ArrayList<FamilyMember> familyMembers,boolean hasPet, int age, String petDescription){
            UserList userList = UserList.getInstance();
            User user = new User(UUID.randomUUID(), username, firstName, lastName, phoneNumber, address, password, shelter, familyMembers, hasPet, age, petDescription);
            userList.addUser(user);
            userList.save();
            login(username, password);
            return user;
    }

    public boolean login(String username, String password) {
    UserList userList = UserList.getInstance();
    User user = userList.getUser(username, password);
    if (user != null) {
        currentUser = user;
        return true;
    }
    return false;
    }

    public Request createRequest(ArrayList<RequestType> requestTypes, Address address,
        int reportedUrgency, ArrayList<String> comments) {
            if (currentUser == null || requestTypes == null || requestTypes.isEmpty()) {
                return null;
            }
            Request request = new Request(null, RequestStatus.PENDING, address, reportedUrgency,
                comments, currentUser, null, requestTypes);
            request.calcPriority();
            feed.addRequest(request);
            return request;
    }

    public ArrayList<Request> searchRequests(String key){
        System.out.println("\nSearch results for: " + key+"\n");
        ArrayList<Request> requests = feed.searchRequests(key);
        for(Request request: requests)
            System.out.println(request.toString() + "\n");
        return requests;
    }

    public ArrayList<Request> findRequests(User user){
        System.out.println("\n\nFindng relevant requests:\n");
        ArrayList<Request> requests = feed.findRelevantRequests(user);
        for(Request request: requests)
            System.out.println(request.toString() + "\n");
        return requests;
    }

    public void displayRequests(){
        System.out.println("\n\nAll Requests:\n");
        for(Request request : feed.getRequests())
            System.out.println(request.toString() + "\n");
    }

    public void findRequestsByLocation(Address address){
        System.out.println("\n\nRequests for: "+ address.toString() +"\n");
        boolean match = false;
        for(Request request : feed.getRequests()){
            if(request.getAddress() == address){
                System.out.println(request.toString() + "\n");
                match = true;
            }
        }
    }
    public Request findClosestRequest(User user){
        try{
            Address address = MapTools.getInstance().findClosestAddress(user.getAddress(),shelterList.getAddresses());
            for(Request request : feed.getRequests()){
                if(address.equals(request.getAddress())){
                    return request;
                }
            }

        } catch (Exception e) {
            System.out.println("There was a problem finding a shelter.");
            e.printStackTrace();
        }
        return null;
    }

        public Shelter findClosestShelter(User user){
        try{
            Address address = MapTools.getInstance().findClosestAddress(user.getAddress(),shelterList.getAddresses());
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

    public Request findClosestRequest(User user, ArrayList<Request> requests){
        try{
            Address[] addresses = new Address[requests.size()];
            for(int i = 0; i < requests.size(); i++)
                addresses[i] = requests.get(i).getAddress();
            Address address = MapTools.getInstance().findClosestAddress(user.getAddress(),addresses);
            for(Request request : feed.getRequests()){
                if(address.equals(request.getAddress())){
                    return request;
                }
            }

        } catch (Exception e) {
            System.out.println("There was a problem finding a request.");
            e.printStackTrace();
        }
        return null;
    }

    public void getUnfulfilledRequests(){
        System.out.println("\n\nUnfulfilled Requests:\n");
        for(Request request : feed.getRequests()){
            if(request.getResponder() == null)
                System.out.println(request.toString() + "\n");
        }
    }

    public void logout() {
        ShelterList.getInstance().save();
        UserList.getInstance().save();
        feed.save();
        currentUser = null;
        System.out.println("Logout successful");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /* Finds user by username
    @param username = the username
    @return the user or null if none */
    public User getUser(String username) {
        return UserList.getInstance().getUser(username);
    }

    // --------------- match volunteers to request

    /* Gets driving distance between two locations
    @param from = where to start
    @param to = where to end
    @return the driving distance in miles */
    // public double getDrivingMiles(Location from, Location to) {
    //     return;
    // }

    /* Checks whether a request needs a current user's skills
    @param request = the request
    @return true if request matches user's skills */
    public boolean matchesSkill(Request request) {
        for (RequestType type : request.getRequestTypes()) {
            if (currentUser.getSkills().contains(type.skill)) {
                return true;
            }
        }
        return false;
    }

    /* a request is recomended when it matches the user's skills and is within nearby miles
    @param request = the request
    @return true if request fits skills and is within miles */
    // public boolean isRecomended(Request request) {
    //     return matchesSkill(request) && getDrivingMiles(currentUser.getLocation(), request.getLocation()) <= NEARBY_MILES;
    // }
}