import java.util.ArrayList;


public class ReliefFacade {
    private Hurricane hurricane;
    private User currentUser;

    public ReliefFacade() {
        hurricane = DataLoader.getHurricane();
        currentUser = null;
    }

    public void submitRequest(User requester, Request request, Location loc, String comments) {
        request.setRequester(requester);
        request.setLocation(loc);
        request.addComment(comments);
        RequestFeed.getInstance().addRequest(request);
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
    ArrayList<Request> requests = RequestFeed.getInstance().getAllRequests();
    for (Request request : requests) {
        if (request.getStatus() == RequestStatus.PENDING) {
            request.setResponder(user);
            request.updateStatus(RequestStatus.HELP_ON_THE_WAY);
            return;
        }
    }
}

    public void restockShelter(Admin admin, Shelter shelter,
        ResourceType type, int quantity) {
    if (shelter != null) {
        shelter.restock(type, quantity);
    }
}

    public Hurricane getStormStatus() {
        return hurricane;
    }

    public RequestFeed browseRequests() {
        return RequestFeed.getInstance();

    }

    public void login(String username, String password) {
        UserList userList = UserList.getInstance();
        User user = userList.getUser(username, password);
        if (user!=null){
            currentUser = user;
        }
    }

    public void createAccount(String username, String firstName,
        String lastName, Location location, String phoneNumber,
        String address, String password){
            UserList userList = UserList.getInstance();
            userList.addUser(username,firstName, lastName, location, phoneNumber, address, password);
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

public void logout() {
    currentUser = null;
    System.out.println("Logout successful");
}

public User getCurrentUser() {
    return currentUser;
}
}