package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        ReliefFacade reliefFacade = new ReliefFacade();
        ShelterList shelterList = ShelterList.getInstance();
        RequestFeed requestFeed = RequestFeed.getInstance();
        HurricaneHandler hurricaneHandler = HurricaneHandler.getInstance();
        Hurricane hurricane = hurricaneHandler.getHurricane("Katrina");

        UserList userList = UserList.getInstance();
        System.out.println(userList.getUser("kyled").getAddress());

        reliefFacade.login("katKane", "brutus51");
        System.out.println(reliefFacade.getCurrentUser().getUsername());
        ArrayList<String> family = reliefFacade.getCurrentUser().getFamilyMembers();
        for(String member : family)
            System.out.println(member);

        ArrayList<String> fm = new ArrayList<>();
        reliefFacade.createAccount("hello","hell","o",null,"82983","unknown","jeniwde",shelterList.getShelter("Caldwell Elementary School"),fm);
        System.out.println(reliefFacade.getCurrentUser().getFirstName());

        Shelter shelter = shelterList.getShelter("Caldwell Elementary School");
        System.out.println(shelter.getCapacity());
        ArrayList<Resource> resources = shelter.getResources();
        //for(Resource resource : resources)
          //  System.out.println(resource.toString());

        System.out.println(reliefFacade.getCurrentUser().getShelter().getName());

        reliefFacade.logout();

        Request tempRequest = requestFeed.getRequest(UUID.fromString("88eb85df-d238-4a83-a0f5-e5aa5948d6ff"));
        System.out.println(tempRequest.getStatus().toString() + tempRequest.calcPriority());
        System.out.println(hurricane.getConditions());

        System.out.println(userList.getUser("hwells").getLocation().getCity());
        Location loc = shelterList.getShelter("Saint Jude's Church").getLocation();
        System.out.println(loc.getState());
    }
}
