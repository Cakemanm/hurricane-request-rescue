package com.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;

public class DataWriterTest {
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

        users = new ArrayList<>();
        shelters = new ArrayList<>();
        address = new Address("1250 Main St", "Columbia", "SC", "29205");

        DataWriter.saveUsers(users);
        DataWriter.saveShelters(shelters);
    }

    // restores json files after tests are run
    @After 
    public void reset(){
        DataWriter.saveUsers(originalUsers);
        DataWriter.saveShelters(originalShelters);
    }

    // tests writing a user list with no users
    @Test 
    public void testWritingEmptyUserList(){
        DataWriter.saveUsers(users);
        assertTrue(DataLoader.getUsers().isEmpty());
    }

    // tests writing a new user
    @Test 
    public void testWritingUser(){
        User user = new User(
                null, "bobrob", "Bob", "Robertson",
                "8038467183", address, "Nam3sB0b!@",
                null, null, false, 37, null);

        users.add(user);
        DataWriter.saveUsers(users);

        ArrayList<User> loaded = DataLoader.getUsers();

        assertEquals(1, loaded.size());
        assertEquals(user.getID(), loaded.get(0).getID());
        assertEquals("bobrob", loaded.get(0).getUsername());
        assertEquals("Bob", loaded.get(0).getFirstName());
        assertEquals(37, loaded.get(0).getAge());
        assertNull(loaded.get(0).getShelter());
    }

    // tests writing multiple users at once
    @Test 
    public void testWritingManyUsers(){
        users.add(new User(
                null, "bobrob", "Bob", "Robertson",
                "8038467183", address, "Nam3sB0b!@",
                null, null, false, 37, null));

        users.add(new User(
                null, "janedoe", "Jane", "Doe",
                "8035551234", address, "JanePass123!",
                null, null, false, 28, null));

        DataWriter.saveUsers(users);

        ArrayList<User> loaded = DataLoader.getUsers();

        assertEquals(2, loaded.size());
        assertEquals("bobrob", loaded.get(0).getUsername());
        assertEquals("janedoe", loaded.get(1).getUsername());
    }

    // tests writing a shelter list with no shelters
    @Test 
    public void testWritingEmptyShelterList(){
        DataWriter.saveShelters(shelters);
        assertTrue(DataLoader.getShelters().isEmpty());
    }

    // tests writing a new shelter
    @Test
    public void testWritingShelter(){
        UUID id = UUID.randomUUID();

        shelters.add(new Shelter(
                "Riverside Middle School", true, null,
                address, null, 250, 300, id, 20, 5));

        DataWriter.saveShelters(shelters);

        ArrayList<Shelter> loaded = DataLoader.getShelters();

        assertEquals(1, loaded.size());

        Shelter shelter = loaded.get(0);

        assertEquals(id, shelter.getId());
        assertEquals("Riverside Middle School", shelter.getName());
        assertEquals(250, shelter.getOccupancy());
        assertEquals(300, shelter.getCapacity());
        assertEquals(20, shelter.getPetCapacity());
        assertEquals(5, shelter.getPetCount());
        assertEquals("1250 Main St", shelter.getAddress().getStreet());
    }

    // tests for an error writing a null user list
    @Test(expected = NullPointerException.class)
    public void testWritingNullUserList(){
        DataWriter.saveUsers(null);
    }

    // tests for an error writing a null user
    @Test(expected = NullPointerException.class)
    public void testWritingNullUser(){
        users.add(null);
        DataWriter.saveUsers(users);
    }

    // tests if a null user address can be written
    @Test(expected = NullPointerException.class)
    public void testWritingUserWithNullAddress(){
        users.add(new User(null, "bobrob", "Bob", "Robertson", "8038467183",  null,
            "Nam3sB0b!@", null, null, false, 37, null));
        DataWriter.saveUsers(users);
    }

    // tests if a shelter can have a missing UUID and be written
    @Test 
    public void testShelterWithMissingID(){
        shelters.add(new Shelter("Riverside Middle School", true, null, address,
         null, 250, 300, null));
        DataWriter.saveShelters(shelters);

        assertThrows(IllegalStateException.class, () -> {
            DataLoader.getShelters();
        });
    }
}
