package com.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.location.Address;
import java.util.UUID;
public class DataLoaderTest {
    private UserList userList = UserList.getInstance();
    private ArrayList<User> users = userList.getUsers();
    private ShelterList shelterList = ShelterList.getInstance();
    private ArrayList<Shelter> shelters = shelterList.getShelters();
    private ReliefFacade rf = new ReliefFacade();
    
    @Before 
    public void setup(){
        users.clear();
        Address address = new Address("1250 Main St", "Columbia", "SC", "29205");
        users.add(new User(null, "bobrob", "Bob", "Robertson", "8038467183", address,
        "Nam3sB0b!@", null, null, false, 37, null));
        DataWriter.saveUsers(users);
        shelters.clear();
        shelters.add(new Shelter("Riverside Middle School", true, null, 
        address, null, 250, 300, UUID.randomUUID()));
        DataWriter.saveShelters(shelters);
        
    }

    @After 
    public void reset(){
        users.clear();
        DataWriter.saveUsers(users);
        shelters.clear();
        DataWriter.saveShelters(shelters);
    }

    @Test 
    public void testGetUserUsernameFromLoader(){
        users = DataLoader.getUsers();
        assertEquals("bobrob", users.get(0).getUsername());
    }

    @Test
    public void testGetUserFirstNameFromLoader(){
        users = DataLoader.getUsers();
        assertEquals("Bob", users.get(0).getFirstName());
    }


    @Test
    public void testGetShelterOccupancyFromLoader(){
        shelters = DataLoader.getShelters();
        assertEquals(250, shelters.get(0).getOccupancy());
    }

    @Test
    public void testGetShelterAvailabilityFromLoader(){
        shelters = DataLoader.getShelters();
        boolean availability = shelters.get(0).getAvailability(250, 300);
        assertTrue(availability);
    }
}
