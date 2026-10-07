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

    public Shelter signUpVictimForShelter(Victim victim,
            Location preferredLocation) {
        // TODO: implement later
        return null;
    }

    public void dispatchVolunteer(Volunteer volunteer, Request request) {
        // TODO: implement later
    }

    public void restockShelter(Admin admin, Shelter shelter,
            ResourceType type, int quantity) {
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
        }
    }

    public void createAccount(String username, String firstName,
        String lastName, Location location, String phoneNumber,
        String address, String password){
            UserList userList = UserList.getInstance();
            userList.addUser(usernmae,firstnmae, lastName, location, phoneNumber, address, password);
            login(username, password);
       
    }

    public void logout() {
        // TODO: implement later
    }
}