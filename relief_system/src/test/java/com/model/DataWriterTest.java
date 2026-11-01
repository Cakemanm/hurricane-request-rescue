package com.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.location.Address;

public class DataWriterTest {
    private ArrayList<User> users;
    private ArrayList<Shelter> shelters;
    private ArrayList<User> originalUsers;
    private ArrayList<Shelter> originalShelters;
    private Address address;

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

    @After 
    public void reset(){
        DataWriter.saveUsers(originalUsers);
        DataWriter.saveShelters(originalShelters);
    }

    @Test 
    public void testWritingEmptyUserList(){
        DataWriter.saveUsers(users);
        assertTrue(DataLoader.getUsers().isEmpty());
    }

    @Test 
    void testWritingUser(){
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
}
