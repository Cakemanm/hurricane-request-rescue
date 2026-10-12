package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        ReliefFacade reliefFacade = new ReliefFacade();
        ShelterList shelterList = ShelterList.getInstance();
        RequestFeed requestList = RequestFeed.getInstance();
        HurricaneHandler hurricaneHandler = HurricaneHandler.getInstance();
        Hurricane hurricane = hurricaneHandler.getHurricane("Katrina");

        UserList userList = UserList.getInstance();
        System.out.println(userList.getUser("kyled").getAddress());

        reliefFacade.login("katKane", "brutus51");
        System.out.println(reliefFacade.getCurrentUser().getUsername());

        ArrayList<String> fm = new ArrayList<>();
        reliefFacade.createAccount("hello","hell","o",null,"82983","unknown","jeniwde",shelterList.getShelter("Caldwell Elementary School"),fm);
        System.out.println(reliefFacade.getCurrentUser().getFirstName());

        
        System.out.println(shelterList.getShelter("Caldwell Elementary School").getCapacity());

        System.out.println(reliefFacade.getCurrentUser().getShelter().getName());

        reliefFacade.logout();

        System.out.println(requestList.getRequest(UUID.fromString("a2170593-1814-427f-9ef6-7e52966a8238")).getStatus());
        System.out.println(hurricane.getConditions());

        System.out.println(userList.getUser("hwells").getLocation().getCity());
        Location loc = shelterList.getShelter("Saint Jude's Church").getLocation();
        System.out.println(loc.getState());
    }
}
