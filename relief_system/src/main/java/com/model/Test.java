package com.model;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ReliefFacade reliefFacade = new ReliefFacade();

        UserList userList = UserList.getInstance();
        System.out.println(userList.getUser("kyled").getAddress());

        reliefFacade.login("katKane", "brutus51");
        System.out.println(reliefFacade.getCurrentUser().getUsername());

        reliefFacade.logout();

        ArrayList<String> fm = new ArrayList<>();
        reliefFacade.createAccount("hello","hell","o",null,"82983","unknown","jeniwde",fm);
        System.out.println(reliefFacade.getCurrentUser().getFirstName());

        ShelterList shelterList = ShelterList.getInstance();
        System.out.println(shelterList.getShelter("Caldwell Elementary School").getCapacity());

        Location loc = shelterList.getShelter("Saint Jude's Church").getLocation();
        System.out.println(loc.getState());
    }
}
