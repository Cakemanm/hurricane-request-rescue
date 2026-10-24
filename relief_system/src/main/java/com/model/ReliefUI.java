package com.model;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import com.location.Address;
public class ReliefUI {
    private ReliefFacade reliefFacade;

    public ReliefUI() {
        reliefFacade = new ReliefFacade();
        scenarioPortia();
    
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

/*
    "id" : "7c3e9a14-5d28-4b6f-a1e0-8f2b4d6c9e35",
    "username" : "sophiesdad",
    "firstName" : "Michael",
    "lastName" : "Carter",
    "location" : "2b8d4f61-9a37-4c05-b3e8-6d1a7c5f0e92",
    "phoneNumber" : "000-843-2271",
    "address" : "67 Somewhere Ave",
    "password" : "Buddy&Sophie09",
    "shelter" : null,
    "skills" : [],
    "familyMembers" : [{"name" : "Laura Carter", "age" : 39}, {"name" : "Sophie Carter", "age" : 9}],
    "hasPet" : true,
    "age" : 41
} */

    public void scenarioPortia() {
        RequestFeed requestFeed = RequestFeed.getInstance();

        //Michael Carter creates an account
        System.out.println("Creating an account for Michael Carter...\n");
        ArrayList<FamilyMember> family = new ArrayList<>();
        family.add(new FamilyMember("Laura Carter",39));
        family.add(new FamilyMember("Sophie Carter",9));
        User michael = reliefFacade.createAccount("sophiesdad","Michael","Carter",null,"000-843-2271","67 Somwhere Ave","Buddy&Sophie09",null,family,true, 41, "Dog named Buddy");

        //Michael Carter logs in
        System.out.println(michael.victimToString());
        System.out.println("\nLogging Michael in...");
        if (!reliefFacade.login("sophiesdad", "Buddy&Sophie09")) {
            System.out.println("Sorry, we couldn't log you in.");
            return;
        }

        User user = reliefFacade.getCurrentUser();
        System.out.println(user.getFullName() + " is now logged in!");
        if (user.getShelter() != null) {
            System.out.println(user.getFullName() + "is currently staying at " + user.getShelter().getName());
        }

        //Michael creates a relief request
        System.out.println("\nCreating a relief request...\n");
        ArrayList<RequestType> types = new ArrayList<>(Arrays.asList(RequestType.TRANSPORTATION, RequestType.NO_SHELTER, RequestType.MEDICAL_ASSISTANCE));
        ArrayList<String> comments = new ArrayList<>(Arrays.asList("My family needs to go to a shelter because our power is out and conditions in our home are worsening my daughter's asthma.","The shelter needs to have medical supplies and staff.","We need transporation to the shelter.","Our dog, Buddy, must come with us."));
        Request request = reliefFacade.createRequest(types,null,"lol",8,comments);
        System.out.println(request.toString());
        reliefFacade.logout();

        //Rosa Delgado logs in
        /*
         * find a way to make (the lines printing when) logging in less repetitive here
         */
        System.out.println("\nRosa Delgado is logging in...");

        if (!reliefFacade.login("rosad", "F350Rosa!trk")) {
            System.out.println("Sorry, we couldn't log you in.");
            return;
        }

        user = reliefFacade.getCurrentUser();
        System.out.println(user.getFullName() + " is now logged in!");
        if (user.getShelter() != null) {
            System.out.println(user.getFullName() + "is currently staying at " + user.getShelter().getName());
        }

        System.out.println("\nFinding requests for Rosa...\n\nThe top requests are nearby and match your specialties:");
        requestFeed.findRelevantRequests(user);
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
            System.out.println(" " + s.getName() + " | " + s.getAddress() + " | "
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
