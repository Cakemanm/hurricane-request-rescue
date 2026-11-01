package com.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.location.Address;
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
        users.add(rf.createAccount("bobrob", "Bob", "Robertson", "8038467183", address,
        "Nam3sB0b!@", null, null, false, 37, null));
        DataWriter.saveUsers(users);
        shelters.clear();
        shelters.add(new Shelter("Riverside Middle School", true, null, 
        address, null, 250, 300, null));
        DataWriter.saveShelters(shelters);
        
    }

    @After 
    public void reset(){
        users.clear();
        DataWriter.saveUsers(users);
        shelters.clear();
        DataWriter.saveShelters(shelters);
    }
}
