package com.model;
import java.util.ArrayList;
import java.util.UUID;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


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

    public void submitRequest(User requester, Request request, Location loc, String comments) {
        request.setRequester(requester);
        request.setLocation(loc);
        request.addComment(comments);
        feed.addRequest(request);
    }

    public void browseShelters(){
        System.out.println("Shelters: ");
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
        return shelter;
    }




    public void dispatchVolunteer(User user) {

        ArrayList<Request> requests = feed.getRequests();   
        for (Request request : requests) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.updateStatus(RequestStatus.HELP_ON_THE_WAY, user);
            return;
        }
    }
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

    public Location createLocation(String address, String city, String state, int zipcode, double latitude, double longitude) {
        return LocationList.getInstance().findOrCreateLocation(address, city, state, zipcode, latitude, longitude);
    }

    public void createAccount(String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password,
        ArrayList<FamilyMember> familyMembers,boolean hasPet, int age, String petDescription){
            UserList userList = UserList.getInstance();
            if (userList.getUser(username) != null) {
                System.out.println("That username is already taken");
                return;
            }
            userList.addUser(new User(UUID.randomUUID(), username, firstName, lastName, location, phoneNumber, address, password, null, familyMembers, hasPet, age, petDescription));
            login(username, password);
    }

    public User createAccount(String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password, Shelter shelter,
        ArrayList<FamilyMember> familyMembers,boolean hasPet, int age, String petDescription){
            UserList userList = UserList.getInstance();
            User user = new User(UUID.randomUUID(), username, firstName, lastName, location, phoneNumber, address, password, shelter, familyMembers, hasPet, age, petDescription);
            userList.addUser(user);
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

    public Request createRequest(ArrayList<RequestType> requestTypes, Location location, String address,
        int reportedUrgency, ArrayList<String> comments) {
            if (currentUser == null || requestTypes == null || requestTypes.isEmpty()) {
                return null;
            }
            Location where = (location == null ? currentUser.getLocation() : location);
            Request request = new Request(null, RequestStatus.PENDING, where, address, reportedUrgency,
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

    public void findRequestsByLocation(Location location){
        System.out.println("\n\nRequests for: "+ location.toString() +"\n");
        boolean match = false;
        for(Request request : feed.getRequests()){
            if(request.getLocation().getId() == location.getId()){
                System.out.println(request.toString() + "\n");
                match = true;
            }
        }
        if(match) return;
        System.out.println("There are no requests for your location. Try another zipcode.");
    }

    public void getUnfulfilledRequests(){
        System.out.println("\n\nUnfulfilled Requests:\n");
        for(Request request : feed.getRequests()){
            if(request.getResponder() == null)
                System.out.println(request.toString() + "\n");
        }
    }

    public void logout() {
        LocationList.getInstance().save();
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