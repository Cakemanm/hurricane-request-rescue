package com.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.location.Address;
import java.util.UUID;
public class DataLoaderTest {
    private ArrayList<User> users;
    private ArrayList<Shelter> shelters;
    private ArrayList<User> originalUsers;
    private ArrayList<Shelter> originalShelters;
    private Address address;
    
    // empties json file before tests are run
    @Before 
    public void setup(){
        originalUsers = DataLoader.getUsers();
        originalShelters = DataLoader.getShelters();

        UUID id = UUID.randomUUID();
        users = new ArrayList<>();
        shelters = new ArrayList<>();
        address = new Address("1250 Main St", "Columbia", "SC", "29205");
        users.add(new User(
                null, "bobrob", "Bob", "Robertson",
                "8038467183", address, "Nam3sB0b!@",
                null, null, false, 37, null));
        shelters.add(new Shelter(
                "Riverside Middle School", true, null,
                address, null, 250, 300, id, 20, 5));
        DataWriter.saveUsers(users);
        DataWriter.saveShelters(shelters);
    }

    // restores json files after tests are run
    @After 
    public void reset(){
        DataWriter.saveUsers(originalUsers);
        DataWriter.saveShelters(originalShelters);
    }

    // tests retrieving the number of users
    @Test 
    public void testLoadingUserCount(){
        ArrayList<User> loaded = DataLoader.getUsers();
        assertEquals(1, loaded.size());
    }

    // tests getting an existing user's username
    @Test 
    public void testGetUserUsernameFromLoader(){
        ArrayList<User> loaded = DataLoader.getUsers();
        assertEquals("bobrob", loaded.get(0).getUsername());
    }

    // tests getting an existing user's last name
    @Test
    public void testGetUserFirstNameFromLoader(){
        ArrayList<User> loaded = DataLoader.getUsers();
        assertEquals("Bob", loaded.get(0).getFirstName());
    }

    // tests loading a user with no skills
    @Test 
    public void testLoadingUserWithEmptySkills(){
        ArrayList<User> loaded = DataLoader.getUsers();
        assertTrue(loaded.get(0).getSkills().isEmpty());
    }

    // tests loading a user with no family members
    @Test 
    public void testLoadingUserWithEmptyFamilyMembers(){
        ArrayList<User> loaded = DataLoader.getUsers();
        assertTrue(loaded.get(0).getSkills().isEmpty());
    }

    // tests loading an empty list of users
    @Test 
    public void testLoadingEmptyUserList(){
        DataWriter.saveUsers(new ArrayList<User>());
        ArrayList<User> loaded = DataLoader.getUsers();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    // tests getting an existing shelter's occupancy
    @Test
    public void testGetShelterOccupancyFromLoader(){
        shelters = DataLoader.getShelters();
        assertEquals(250, shelters.get(0).getOccupancy());
    }

    // tests getting an existing shelter's occupancy
    @Test
    public void testGetShelterAvailabilityFromLoader(){
        shelters = DataLoader.getShelters();
        boolean availability = shelters.get(0).getAvailability(250, 300);
        assertTrue(availability);
    }
}
