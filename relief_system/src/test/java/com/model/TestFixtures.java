package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import com.location.Address;

/**
 * Shared test data for the Reach and Rescue test classes.
 *
 * The singletons (ShelterList, UserList, RequestFeed, HurricaneHandler) keep their
 * data for the whole test run, and every test class runs in the same JVM. So a test class
 * that uses them calls {@link #clearAllData()} in {@code @Before} and
 * {@link #restoreAllData()} in {@code @After}. Each test starts with empty lists, and
 * afterwards the original JSON data is put back, so classes that rely on that data
 * (like ListSearchTesting) still see it.</p>
 * Written by: Jake McNulty
 * AI use: Comment box written with AI and tests checked over to ensure covering all basis.
 *          Some VSCode auto-complete was used to generate forgotten test cases.
 *          TestFixtures class written with AI and reviewed, run, and committed by Jake McNulty
*/

public class TestFixtures {
    private static ArrayList<Shelter> savedShelters;
    private static ArrayList<User> savedUsers;
    private static ArrayList<Request> savedRequests;
    private static ArrayList<Hurricane> savedHurricanes;

    // Remembers whats in every singleton list and then empties them so a test starts from a known state
    public static void clearAllData() {
        ShelterList shelterList = ShelterList.getInstance();
        UserList userList = UserList.getInstance();
        RequestFeed requestFeed = RequestFeed.getInstance();
        HurricaneHandler hurricaneHandler = HurricaneHandler.getInstance();

        savedShelters = new ArrayList<>(shelterList.getShelters());
        savedUsers = new ArrayList<>(userList.getUsers());
        savedRequests = new ArrayList<>(requestFeed.getRequests());
        savedHurricanes = new ArrayList<>(hurricaneHandler.getHurricanes());

        requestFeed.getRequests().clear();
        userList.getUsers().clear();
        shelterList.getShelters().clear();
        hurricaneHandler.getHurricanes().clear();
    }

    /**
     * Puts back the lists saved by {@link #clearAllData()} and writes them to the JSON files,
     * so data a test saved doesn't stay in the files for the next run.
     */
    public static void restoreAllData() {
        if (savedShelters == null)
            return;
        replace(ShelterList.getInstance().getShelters(), savedShelters);
        replace(UserList.getInstance().getUsers(), savedUsers);
        replace(RequestFeed.getInstance().getRequests(), savedRequests);
        replace(HurricaneHandler.getInstance().getHurricanes(), savedHurricanes);
        savedShelters = null;

        // clean-up only, don't throw exceptions to the test runner
        try {
            ShelterList.getInstance().save();
            UserList.getInstance().save();
            RequestFeed.getInstance().save();
        } catch (Exception e) {
            // should be ignored, but print to console so we can see it if it happens
            System.err.println("Error writing JSON files in restoreAllData(): " + e);
        }
    }

    private static <T> void replace(ArrayList<T> list, ArrayList<T> newContents) {
        list.clear();
        list.addAll(newContents);
    }

    // -- addresses

    public static Address carterHome() {
        return new Address("68 Calhoun Street", "Charleston", "SC", "29401");
    }

    public static Address rosaHome() {
        return new Address("100 Aquarium Wharf", "Charleston", "SC", "29401");
    }

    public static Address riversideAddress() {
        return new Address("3841 Leeds Avenue", "North Charleston", "SC", "29401");
    }

    // -- users

    // Victim from the scenario: Michael Carter, 2 family members, a dog, no skills.
    public static User michaelCarter() {
        ArrayList<FamilyMember> family = new ArrayList<>();
        family.add(new FamilyMember("Laura Carter", 39));
        family.add(new FamilyMember("Sophie Carter", 9));
        return new User(UUID.fromString("11111111-1111-1111-1111-111111111111"), "sophiesdad", "Michael", "Carter",
                "000-843-2271", carterHome(), "Buddy&Sophie09", null, family, new ArrayList<>(), true, 41,
                "Dog named Buddy");
    }

    // Volunteer from the scenario: Rosa Delgado, skills CAN_DRIVE and TRANSPORTATION.
    public static User rosaDelgado() {
        return new User(UUID.fromString("22222222-2222-2222-2222-222222222222"), "rosad", "Rosa", "Delgado",
                "000-843-5590", rosaHome(), "F350Rosa!trk", null, new ArrayList<>(),
                new ArrayList<>(Arrays.asList(Skill.CAN_DRIVE, Skill.TRANSPORTATION)), false, 45, null);
    }

    // Shelter manager from the scenario: Felix Reyes, skills SHELTER_STAFF and RESOURCE_SUPPLIER.
    public static User felixReyes() {
        return new User(UUID.fromString("33333333-3333-3333-3333-333333333333"), "freyes", "Felix", "Reyes",
                "000-486-9462", riversideAddress(), "FReyesSh3lt3r$", null, new ArrayList<>(),
                new ArrayList<>(Arrays.asList(Skill.SHELTER_STAFF, Skill.RESOURCE_SUPPLIER)), false, 41, null);
    }

    // -- shelters

    public static Shelter riverside() {
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(new Resource(ResourceType.WATER, 12));
        resources.add(new Resource(ResourceType.FOOD, 24));
        return new Shelter("Riverside Community Center", true, resources, riversideAddress(),
                shelterTypes(ShelterType.DISABILITY_ACCESSIBLE, ShelterType.PET_FRIENDLY, ShelterType.MEDICAL_STAFF),
                140, 300, UUID.fromString("3f9a1c52-7d84-4e1b-a6c3-2b5e8f0d9a41"), 10, 0);
    }

    // -- requests

    public static Request pendingRequest(User requester, RequestType types) {
        return new Request(null, RequestStatus.PENDING, requester.getAddress(), 8,
                        comments("Please send help."), requester, null, requestTypes(types));
    }

    // -- list helpers
    public static ArrayList<RequestType> requestTypes(RequestType... types) {
        return new ArrayList<>(Arrays.asList(types));
    }

    public static ArrayList<ShelterType> shelterTypes(ShelterType... types) {
        return new ArrayList<>(Arrays.asList(types));
    }

    public static ArrayList<String> comments(String... comments) {
        return new ArrayList<>(Arrays.asList(comments));
    }
}