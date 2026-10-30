package com.model;
import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.location.Address;
import com.location.MapTools;
/**
 * How the UI interacts with the code, acting as an interface
 * to pass work to singletons {@link UserList}, {@link ShelterList}, {@link RequestFeed}, {@link HurricaneHandler}
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */

public class ReliefFacade {
    private ArrayList<Hurricane> hurricane;
    private User currentUser;
    private RequestFeed feed;
    private ShelterList shelterList = ShelterList.getInstance();

    /** Requests within this many driving miles of volunteer to be recomended */
    public static final double NEARBY_MILES = 10.0;

    /** Folder trip sheets are exported to */
    public static final String EXPORT_FOLDER = "exports";

    /**
     * Creates facade with no one logged in
     */
    public ReliefFacade() {
        hurricane = DataLoader.getHurricanes();
        feed = RequestFeed.getInstance();
        currentUser = null;
    }

    /**
     * Submits a relief request
     * @param requester - user submitting
     * @param request - request to submit
     * @param comments - additional comments
     */
    public void submitRequest(User requester, Request request, String comments) {
        request.setRequester(requester);
        request.addComment(comments);
        feed.addRequest(request);
    }

    /**
     * Prints list of shelters
     */
    public void browseShelters(){
        System.out.println("Recommended Shelters: ");
        for(Shelter shelter : shelterList.getShelters()){
            System.out.println(shelter.toString() + "\n");
        }
    }

    /**
     * Retrieves list of shelters
     * @return
     */
    public ArrayList<Shelter> getShelters() {
        return ShelterList.getInstance().getShelters();
    }

    /**
     * Registers the current user at the named shelter and saves the shelter and user lists.
     * If the user belongs to another shelter, removes them from that shelter first.
     * @param shelterName the name of the shelter to join
     * @return the selected shelter, or {@code null} if no user is logged in or the shelter is not found
     */
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

    /**
     * Assigns a volunteer to a pending request and changes its status to help on the way.
     * @param request the request to assign
     * @param user the volunteer responding to the request
     * @return {@code true} if the request was pending and was updated; {@code false} otherwise
     */
    public boolean dispatchVolunteer(Request request, User user) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.updateStatus(RequestStatus.HELP_ON_THE_WAY, user);
            return true;
        }
        return false;
    }

    /**
     * Restocks a shelter with the specified resource. Does nothing if the shelter is {@code null}.
     * @param shelter the shelter to restock
     * @param type the type of resource to add
     * @param quantity the quantity to add
     */
    public void restockShelter(Shelter shelter, ResourceType type, int quantity) {
    if (shelter != null) {
        shelter.restock(type, quantity);
    }
    }

    /**
     * Retrieves the hurricanes loaded by this facade.
     * @return the list of hurricanes
     */
    public ArrayList<Hurricane> getStormStatus() {
        return hurricane;
    }

    /**
     * Retrieves the relief request feed.
     * @return the request feed used by this facade
     */
    public RequestFeed browseRequests() {
        return feed;

    }

        /**
     * Creates and saves an account with the specified shelter, then attempts to log the user in.
     * @param username the username for the new account
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param phoneNumber the user's phone number
     * @param address the user's address
     * @param password the password for the new account
     * @param shelter the shelter assigned to the user
     * @param familyMembers the user's family members
     * @param hasPet whether the user has a pet
     * @param age the user's age
     * @param petDescription a description of the user's pet
     * @return the newly created user
     */
    public User createAccount(String username, String firstName, String lastName, String phoneNumber, Address address, String password, Shelter shelter,
        ArrayList<FamilyMember> familyMembers,boolean hasPet, int age, String petDescription){
            UserList userList = UserList.getInstance();
            User user = new User(UUID.randomUUID(), username, firstName, lastName, phoneNumber, address, password, shelter, familyMembers, hasPet, age, petDescription);
            userList.addUser(user);
            userList.save();
            login(username, password);
            return user;
    }

    /**
     * Attempts to log in using the supplied credentials.
     * Sets the current user when a matching account is found.
     * @param username the account username
     * @param password the account password
     * @return {@code true} if a matching account is found; {@code false} otherwise
     */
    public boolean login(String username, String password) {
        UserList userList = UserList.getInstance();
        User user = userList.getUser(username, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }


    /**
     * Creates a pending request for the current user, calculates its priority, and adds it to the feed.
     * @param requestTypes the types of assistance requested
     * @param address the address where assistance is needed
     * @param reportedUrgency the urgency reported by the user
     * @param comments additional information about the request
     * @return the created request, or {@code null} if no user is logged in or no request types are provided
     */
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

    /**
     * Searches the request feed and prints the matching requests.
     * @param key the search term passed to the request feed
     * @return the matching requests
     */
    public ArrayList<Request> searchRequests(String key){
        System.out.println("\nSearch results for: " + key+"\n");
        ArrayList<Request> requests = feed.searchRequests(key);
        for(Request request: requests)
            System.out.println(request.toString() + "\n");
        return requests;
    }

    /**
     * Finds and prints requests relevant to the specified user.
     * @param user the user for whom to find relevant requests
     * @return the relevant requests returned by the feed
     */
    public ArrayList<Request> findRequests(User user){
        System.out.println("\n\nFindng relevant requests:\n");
        ArrayList<Request> requests = feed.findRelevantRequests(user);
        for(Request request: requests)
            System.out.println(request.toString() + "\n");
        return requests;
    }

    /**
     * Prints all requests in the feed.
     */
    public void displayRequests(){
        System.out.println("\n\nAll Requests:\n");
        for(Request request : feed.getRequests())
            System.out.println(request.toString() + "\n");
    }

    /**
     * Finds the closest request address to the user's address and returns a request at that address.
     * Prints an error if the lookup fails.
     * @param user the user whose address is the starting location
     * @return a request at the closest address, or {@code null} if none is found or an error occurs
     */
    public Request findClosestRequest(User user){
        try{
            Address address = MapTools.getInstance().findClosestAddress(user.getAddress(),feed.getAddresses());
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

    /**
     * Finds the closest shelter to the specified user using the shelter list.
     * @param user the user for whom to find a shelter
     * @return the result of the shelter list's closest-shelter lookup
     */
    public Shelter findClosestShelter(User user){
        return shelterList.findClosestShelter(user, shelterList.getShelters());
    }

    /**
     * Finds the closest address among the supplied requests and returns the first request in the feed at that address.
     * Prints an error if the lookup fails.
     * @param user the user whose address is the starting location
     * @param requests the requests whose addresses are considered
     * @return a request from the feed at the closest address, or {@code null} if none is found or an error occurs
     */
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

    /**
     * Prints all requests in the feed that have no assigned responder.
     */
    public void getUnfulfilledRequests(){
        System.out.println("\n\nUnfulfilled Requests:\n");
        for(Request request : feed.getRequests()){
            if(request.getResponder() == null)
                System.out.println(request.toString() + "\n");
        }
    }

    /**
     * Saves the shelter list, user list, and request feed, clears the current user, and prints a logout message.
     */
    public void logout() {
        ShelterList.getInstance().save();
        UserList.getInstance().save();
        feed.save();
        currentUser = null;
        System.out.println("Logout successful");
    }

    /**
     * Retrieves the user currently logged in.
     * @return the current user, or {@code null} if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /** Finds user by username
    @param username = the username
    @return the user or null if none */
    public User getUser(String username) {
        return UserList.getInstance().getUser(username);
    }

    // --------------- match volunteers to request

    /** Checks whether a request needs a current user's skills
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

    // --------export to txt

    /**
     * Exports the requester's household, contact information, pickup address, and assistance notes to a text trip sheet.
     * Writes the file in {@link #EXPORT_FOLDER}, using the requester's last name in the filename.
     * @param request the request to include in the trip sheet
     * @param shelter the shelter parameter, currently unused
     * @return the exported file, or {@code null} if an I/O error occurs
     */
    public File exportTripSheet(Request request, Shelter shelter) {
        User family = request.getRequester();
        User volunteer = request.getResponder() == null ? currentUser : request.getResponder();
        String line = "=".repeat(60);
        StringBuilder sheet = new StringBuilder();
        sheet.append(line).append("\n Request Rescue Trip Sheet").append(line);
        sheet.append("Volunteer: ").append(request.getStatus().label).append("\n\n");

        sheet.append("WHO TO PICK UP (").append(family.getHouseholdSize()).append(" people").append(family.hasPet() ? " and has 1 pet" : "").append(")\n");
        sheet.append("  - ").append(family.getFullName()).append(", age ").append(family.getAge()).append("\n");
        for (FamilyMember member : family.getFamilyMembers())
            sheet.append("  - ").append(member.getName()).append(", age ").append(member.getAge()).append("\n");
        if (family.hasPet())
            sheet.append("  - Pet: ").append(family.getPetDescription()).append("\n");

        sheet.append("\nCONTACT\n  ").append(family.getFullName()).append(", ").append(family.getPhoneNumber()).append("\n");
        sheet.append("\nPICKUP ADDRESS\n  ").append(request.getAddress()).append("\n");

        if (request.getRequestTypes().contains(RequestType.MEDICAL_ASSISTANCE)) {
            sheet.append("\nMEDICAL NOTE\n Medical assistance needed. Bring to a shelter with medical staff.\n");
        }
        if (!request.getComments().isEmpty()) {
            sheet.append("\nNOTES FROM THE FAMILY\n");
            for (String comment : request.getComments())
                sheet.append("  - ").append(comment).append("\n");
        }

        //append directions here

        File folder = new File(EXPORT_FOLDER);
        folder.mkdir();
        File file = new File(folder, family.getLastName() + "-trip-sheet.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sheet.toString());
            return file;
        } catch (IOException e) {
            System.out.println("An error occured exporting the trip sheet");
            e.printStackTrace();
            return null;
        }

    }

    // private void appendDirections(StribBuilder sheet, String title, Address from, Address to) {
    //     sheet.append("\n").append(title).append(String.format(" (%.1f mi)", getDrivingMiles(from, to))).append("\n");
    // }

    /**
     * Exports transportation information regarding a certain request to a txt file
     * @param request - request being referenced
     * @param shelter - shelter to get directions to
     * @param startingAddress - starting location
     * @param filename - file to export to
     * @throws Exception - thrown if any issues
     */
    public void exportTransportationPlan(Request request, Shelter shelter,
        Address startingAddress, String filename)
        throws Exception {

    if (request == null || shelter == null || startingAddress == null) {
        throw new IllegalArgumentException(
                "A request, shelter, and starting address are required.");
    }

    User requester = request.getRequester();

    if (requester == null || request.getAddress() == null) {
        throw new IllegalArgumentException(
                "The request must have a requester and pickup address.");
    }

    MapTools maps = MapTools.getInstance();

    String toPickup = String.join("\n",
            maps.getDrivingDirections(startingAddress, request.getAddress()));

    String toShelter = String.join("\n",
            maps.getDrivingDirections(request.getAddress(), shelter.getAddress()));

    try (FileWriter writer = new FileWriter(filename)) {
        writer.write("TRANSPORTATION PLAN\n\n");

        writer.write("HOUSEHOLD MEMBERS\n");
        writer.write(requester.getFullName()
                + ", age " + requester.getAge() + "\n");

        for (FamilyMember member : requester.getFamilyMembers()) {
            writer.write(member + "\n");
        }

        if (requester.hasPet()) {
            String pet = requester.getPetDescription();
            writer.write("Pet: "
                    + (pet == null || pet.isBlank()
                            ? "Description not provided" : pet)
                    + "\n");
        }

        writer.write("\nCONTACT AND PICKUP\n");
        writer.write("Contact: " + requester.getFullName() + "\n");
        writer.write("Phone: " + requester.getPhoneNumber() + "\n");
        writer.write("Pickup: " + request.getAddress() + "\n");

        writer.write("\nASSISTANCE NOTES\n");
        if (request.getComments() != null) {
            for (String comment : request.getComments()) {
                writer.write(comment + "\n");
            }
        }

        writer.write("\nDIRECTIONS TO PICKUP\n");
        writer.write("From: " + startingAddress + "\n");
        writer.write("To: " + request.getAddress() + "\n");
        writer.write(toPickup + "\n");

        writer.write("\nDIRECTIONS TO SHELTER\n");
        writer.write("From: " + request.getAddress() + "\n");
        writer.write("To: " + shelter.getAddress() + "\n");
        writer.write(toShelter + "\n");

        writer.write("\nSELECTED SHELTER\n");
        writer.write(shelter.getName() + "\n");
        writer.write(shelter.getAddress() + "\n");
    }
}
}