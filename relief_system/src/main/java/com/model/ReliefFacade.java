package com.model;
import java.util.ArrayList;
import java.util.UUID;


public class ReliefFacade {
    private ArrayList<Hurricane> hurricane;
    private User currentUser;
    private RequestFeed feed = RequestFeed.getInstance();
    private ShelterList shelters = ShelterList.getInstance();

    public ReliefFacade() {
        hurricane = DataLoader.getHurricanes();
        currentUser = null;
    }

    public void submitRequest(User requester, Request request, Location loc, String comments) {
        request.setRequester(requester);
        request.setLocation(loc);
        request.addComment(comments);
        RequestFeed.getInstance().getRequest(request);
    }

    public void browseShelters(){
        System.out.println("Shelters: ");
        for(Shelter shelter : shelters.getShelters()){
            System.out.println(shelter.toString() + "\n");
        }
    }

    public Shelter signUpVictimForShelter(User user) {
    ArrayList<Shelter> shelters = ShelterList.getInstance().getShelters();
    for (Shelter shelter : shelters) {
        if (shelter.isAvailable() && shelter.getOccupancy() < shelter.getCapacity()) {
            shelter.addOccupant();
            user.setShelter(shelter.getId());
            return shelter;
        }
    }
    return null;
    }


    public void dispatchVolunteer(User user) {

        ArrayList<Request> requests = RequestFeed.getInstance().getRequests();   
        for (Request request : requests) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setResponder(user);
            request.updateStatus(RequestStatus.HELP_ON_THE_WAY);
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
        return RequestFeed.getInstance();

    }

    public void createAccount(String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password,
        ArrayList<String> familyMembers){
            UserList userList = UserList.getInstance();
            userList.addUser(UUID.randomUUID(), username, firstName, lastName, location, phoneNumber, address, password, null, familyMembers);
            login(username, password);
    }

    public void createAccount(String username, String firstName, String lastName, 
        Location location, String phoneNumber, String address, String password, Shelter shelter,
        ArrayList<String> familyMembers){
            UserList userList = UserList.getInstance();
            userList.addUser(UUID.randomUUID(), username, firstName, lastName, location, phoneNumber, address, password, shelter, familyMembers);
            login(username, password);
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


    public ArrayList<Request> searchRequests(String key){
        System.out.println("\nSearch results for: " + key+"\n");
        ArrayList<Request> requests = feed.searchRequests(key);
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
        currentUser = null;
        System.out.println("Logout successful");
    }

    public User getCurrentUser() {
        return currentUser;
    }
}