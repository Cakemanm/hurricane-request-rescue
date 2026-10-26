package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.location.Address;
import com.location.MapTools;
public class ReliefUI {
    Address address = new Address("68 Calhoun Street","Charleston","SC","29041");
    private ReliefFacade reliefFacade;
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
 

    public ReliefUI() {
        reliefFacade = new ReliefFacade();
        scenarioPortia();
    
    }

    public void run() {
        // scenario1();
        // scenario2();
    }

    public void scenarioPortia() {
        RequestFeed requestFeed = RequestFeed.getInstance();
        ShelterList shelterList = ShelterList.getInstance();

        System.out.println(GREEN + "----CREATING USER ACCOUNT AND RELIEF REQUEST----" + RESET);
        //Michael Carter creates an account
        System.out.println(BLUE + "Creating an account for Michael Carter...\n" + RESET);
        ArrayList<FamilyMember> family = new ArrayList<>();
        family.add(new FamilyMember("Laura Carter",39));
        family.add(new FamilyMember("Sophie Carter",9));
        /* 
        Address cAddress = new Address("1 Vendue Range", "Charleston", "SC", "29401");
        User michael = reliefFacade.createAccount("sophiesdad","Michael","Carter","000-843-2271", cAddress,"Buddy&Sophie09",null,family,true, 41, "Dog named Buddy");
*/
        Address address = new Address("68 Calhoun Street","Charleston","SC","29401");
        User michael = reliefFacade.createAccount("sophiesdad","Michael","Carter","000-843-2271",address,"Buddy&Sophie09",null,family,true, 41, "Dog named Buddy");

        //Michael Carter logs in
        System.out.println(michael.victimToString());
        System.out.println(BLUE + "\nLogging Michael in..." + RESET);
        if (!reliefFacade.login("sophiesdad", "Buddy&Sophie09")) {
            System.out.println("Sorry, something went wrong! We couldn't log you in.");
            return;
        }

        User user = reliefFacade.getCurrentUser();
        System.out.println(BLUE+ user.getFullName() + " is now logged in!" + RESET);
        if (user.getShelter() != null) {
            System.out.println(user.getFullName() + "is currently staying at " + user.getShelter().getName());
        }

        //Michael creates a relief request
        System.out.println(BLUE + "\nCreating a relief request...\n" + RESET);
        ArrayList<RequestType> types = new ArrayList<>(Arrays.asList(RequestType.TRANSPORTATION, RequestType.NO_SHELTER, RequestType.MEDICAL_ASSISTANCE));
        ArrayList<String> comments = new ArrayList<>(Arrays.asList("My family needs to go to a shelter because our power is out and conditions in our home are worsening my daughter's asthma.","The shelter needs to have medical supplies and staff.","We need transportation to the shelter.","Our dog, Buddy, must come with us."));
        Request request = reliefFacade.createRequest(types,address,8,comments);
        System.out.println(request.toString());
        System.out.println(BLUE);
        reliefFacade.logout();

        //Rosa Delgado logs in
        /*
         * find a way to make (the lines printing when) logging in less repetitive here
         */
        System.out.println(GREEN + "----MATCHING A VOLUNTEER WITH THE REQUEST----" + RESET);
        System.out.println(BLUE + "\nRosa Delgado is logging in..." + RESET);

        if (!reliefFacade.login("rosad", "F350Rosa!trk")) {
            System.out.println("Sorry, something went wrong! We couldn't log you in.");
            return;
        }

        user = reliefFacade.getCurrentUser();
        System.out.println(user.getFullName() + " is now logged in!");
        if (user.getShelter() != null) {
            System.out.println(user.getFullName() + "is currently staying at " + user.getShelter().getName());
        }

        System.out.println(BLUE + "\nFinding requests for Rosa...\n"+RESET+"\n\n Here are the top requests nearby that match your specialties:");
        requestFeed.findRelevantRequests(user);

        // find shelter and assign request
        System.out.println(GREEN + "----CLAIMING THE REQUEST AND FINDING A SHELTER----" + RESET);
        //Rosa claims the request
        if(reliefFacade.dispatchVolunteer(request, user))
            System.out.println(BLUE + "\n" + user.getFullName() + " is now the assigned volunteer for the following request:\n" + RESET + request.toString());
        else
            System.out.println("This request claim was unsuccessful.Try again.");

        //Rosa finds a relevant shelter
        System.out.println(BLUE + "\nSearching for shelter's matching the family's criteria...\n\n" + RESET);
        ArrayList<Shelter> matchingShelters = shelterList.findShelters(user,3, true, true, false);
        for(Shelter shelter : matchingShelters)
            System.out.println(shelter.toString()+"\n");

        Shelter chosenShelter = matchingShelters.get(0);
        System.out.println(BLUE + "\nRosa selects " + chosenShelter.getName() + " for the Carter family\n" + RESET);
        
         // export info to txt file
        try {
            reliefFacade.exportTransportationPlan(
            request,
            chosenShelter,
            user.getAddress(),
            "exports\\scenario.txt");

            System.out.println("Transportation plan exported.");
        } catch (Exception e) {
          System.out.println("Export failed: " + e.getMessage());
        }
        // demo role permissions
        System.out.println(BLUE + "Rosa tries to mark the request as complete..." + RESET);
        if(user.getSkills().contains(Skill.SHELTER_STAFF)){
            System.out.println("The current request has been completed!");
            request.setStatus(RequestStatus.COMPLETED);
        } else
            System.out.println("Volunteers are unable to mark this request as complete. See a shelter administrator.");
        reliefFacade.logout();

        //check into shelter
        System.out.println(GREEN + "----CHECKING FAMILY INTO SHELTER----" + RESET);
        System.out.println(BLUE + "\nFelix Reyes is logging in..." + RESET);

        if (!reliefFacade.login("freyes", "FReyesSh3lt3r$")) {
            System.out.println("Sorry, something went wrong! We couldn't log you in.");
            return;
        }

        user = reliefFacade.getCurrentUser();
        System.out.println(user.getFullName() + " is now logged in!");
        if (user.getShelter() != null) {
            System.out.println(user.getFullName() + "is currently staying at " + user.getShelter().getName());
        }

    
        Shelter shelter = user.getShelter();

        if (shelter == null) {
            System.out.println("Felix has no assigned shelter.");
            return;
        }

        if (shelter.getAvailableSpaces() < 3
            || shelter.getAvailablePetSpaces() < 1) {
            System.out.println("Not enough room for the family and Buddy.");
            return;
        }

        shelter.addOccupants(3, 1);
        ShelterList.getInstance().save();

        System.out.println("The Carter Family and Buddy have been checked into "
             + shelter.getName() + "!");
        System.out.println("Occupancy: " + shelter.getOccupancy()
              + "/" + shelter.getCapacity());
        System.out.println("Remaining spaces: " + shelter.getAvailableSpaces());
        System.out.println("Pets: " + shelter.getPetCount()
             + "/" + shelter.getPetCapacity());

        System.out.println(BLUE + "Felix tries to mark the request as complete..." + RESET);
        if(user.getSkills().contains(Skill.SHELTER_STAFF)){
            System.out.println("The current request has been completed!");
            request.setStatus(RequestStatus.COMPLETED);
        } else
            System.out.println("Volunteers are unable to mark this request as complete. See a shelter administrator.");
        reliefFacade.logout();
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
            //System.out.println(" " + s.getName() + " | " + s.getLocation() + " | "
            //    + s.getOccupancy() + "/" + s.getCapacity() + (s.isAvailable() ? " | Open" : " | FULL"));
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
        //Request request = reliefFacade.createRequest(types, null, shelter.getName(), 4, comments);
        System.out.println("\nRequest created:");
        //System.out.println(request);

        reliefFacade.logout();
    }


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
