package com.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.model.User;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.ReliefFacade;

public class DataTesting {
    
    @Test
    public void testSaveUsers(){
        UserList ul = UserList.getInstance();
    }

    @Test
    public void testSaveShelters(){
        ShelterList sl = ShelterList.getInstance();
    }

    @Test
    public void testSaveHurricane(){
        HurricaneHandler hl = HurricaneHandler.getInstance();
    }

    @Test 
    public void testSaveRequests(){
        RequestFeed rf = RequestFeed.getInstance();
    }


}
