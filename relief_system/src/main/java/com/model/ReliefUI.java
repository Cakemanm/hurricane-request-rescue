package com.model;

import java.util.Scanner;
import java.util.ArrayList;

public class ReliefUI {
    private ReliefFacade reliefFacade;

    public ReliefUI() {
        reliefFacade = new ReliefFacade();
    
    }

    public void run() {
        // scenario1();
        // scenario2();
    }

    // create account, submit releif request (include location, contact info, number of people, medical assistance, with dog)
    // match rosa w/ Ford F-350, login, sees 7 requests, family at top (nearby and speciality), search 10 shelters, pick one (w/ dog, medical staff, space for 3 and dog)
    // request status change to assigned. create a .txt file w/ family name & age (+ dog), family contact & pickup, note of kid's medical, directions from rosa to home, directions from home to shelter, shelter name and address
    // prevent rosa from marking complete
    // check family into shelter: felix log into system, see shelter name, location, total capcity, currenct occupancy, available spaces, and lsit of occupants
    // 

    public void scenarioPortia() {

    }

    // login, view shelters, sign up for shelter, view shelter resources, create request

    public void scenario1() {

<<<<<<< HEAD
        // login
        if (!reliefFacade.login("katKane", "brutus51")) {
=======
        if(!reliefFacade.login("hwells", "topS$cr$t")) {
            System.out.println("Sorry, we couldn't log you in");
            return;
        }
        System.out.println("Hannah Wells is now logged in!");
    }

    public void scenario2() {
        if(!reliefFacade.login("kyled", "892EijL0Ws")) {
>>>>>>> carter-branch
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

<<<<<<< HEAD
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
=======
    public static void main(String[] args){
    ReliefUI facadeInterface = new ReliefUI();
    facadeInterface.run();
    
}    
>>>>>>> carter-branch
}
