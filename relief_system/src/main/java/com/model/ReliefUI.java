package com.model;

import java.util.Scanner;
import java.util.ArrayList;

public class ReliefUI {
    private ReliefFacade reliefFacade;

    public ReliefUI() {
        reliefFacade = new ReliefFacade();
    
    }

    public void run() {
        scenario1();
        scenario2();
    }

    // login, view shelters, sign up for shelter, view shelter resources, create request

public void scenario1() {

    // login
    if (!reliefFacade.login("katKane", "brutus51")) {
        System.out.println("Sorry, we couldn't log you in.");
        return;
    }
    User user = reliefFacade.getCurrentUser();
    System.out.println(user.getFullName() + "is now logged in!");
    if (user.getShelter() != null) {
        System.out.println(user.getFullName() + "is currently staying at " + user.getShelter().getName());
    }

    // view shelters
    System.out.println("\nShelters");
    for (Shelter s : reliefFacade.getShelters()) {
        System.out.println(" " + s.getName() + " | " + s.getLocation() + " | "
            + s.getOccupancy() + "/" + s.getCapacity() + (s.isAvailable() ? " | Open" : " | FULL"));
    }

    // sign up for shelter
    Shelter shelter = reliefFacade.signUpForShelter("Caldwell Elementary School");
    if (shelter == null) {
        System.out.println("\nSorry, that shelter is full or does not exist");
        return;
    }
    System.out.println("\n" + user.getFirstName() + " signed up for " + shelter.getName()
            + ". Occupancy is now " + shelter.getOccupancy() + "/" + shelter.getCapacity());

    // view shelter resources
    System.out.println("\nResources at " + shelter.getName() + ":");
    for (Resource resouce : shelter.getResources()) {
        System.out.println(" " + resouce);
    }

    // create request
    ArrayList<RequestType> types = new ArrayList<>();
    types.add(RequestType.RESOURCE);
    ArrayList<String> comments = new ArrayList<>();
    comments.add("My mother is ill and needs medicine. Our family also needs blankets for the cold");
    Request request = reliefFacade.createRequest(types, null, shelter.getName(), 4, comments);
    System.out.println("\nRequest created:");
    System.out.println(request);

    reliefFacade.logout();
}

    // public void scenario1() {
    //     System.out.println();

    //     if(!reliefFacade.login("hwells", "topS$cr$t")) {
    //         System.out.println("Sorry, we couldn't log you in");
    //         return;
    //     }
    //     System.out.println("Hannah Wells is now logged in!");
    // }

    // create new account, login user to account, search for requests using "medical" keyword, view unfulfilled requests, claim request
    public void scenario2() {
        System.out.println();
        if(!reliefFacade.login("kyled","892EijL0Ws")) {
            System.out.println("Sorry, we couldn't log you in.");
            return;
        }
        System.out.println("Kyle Donaldson is now logged in!");
    }

    public static void main(String[] args){
    ReliefUI facadeInterface = new ReliefUI();
    facadeInterface.run();
}    
}
