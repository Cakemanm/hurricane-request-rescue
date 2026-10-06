package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants{
    public static ArrayList<User> users = new ArrayList<User>();

    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();
    try{
        FileReader reader = new FileReader(USER_FILE_NAME);
        JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

        for(int i = 0; i < peopleJSON.size(); i++){
            JSONObject personJSON = (JSONObject)peopleJSON.get(i);

            //UUID id = UUID.fromString((String)personJSON.get(USER_ID));
            String username = (String)personJSON.get(USER_USERNAME);
            String firstName = (String)personJSON.get(USER_FIRST_NAME);
            String lastName = (String)personJSON.get(USER_LAST_NAME);
            //UUID location = UUID.fromString((String)personJSON.get(USER_LOCATION));
            String phoneNumber = (String)personJSON.get(USER_PHONE_NUMBER);
            String address = (String)personJSON.get(USER_ADDRESS);
            String password = (String)personJSON.get(USER_PASSWORD);
            //UUID shelter = UUID.fromString((String)personJSON.get(USER_SHELTER));
            boolean hasPet = (boolean)personJSON.get(USER_HAS_PET);

            JSONArray skillsJSON = (JSONArray)personJSON.get(USER_SKILLS);
            ArrayList<String> skills = new ArrayList<>();
            for(Object skill : skillsJSON){
                skills.add((String) skill);
            }

            JSONArray familyJSON = (JSONArray)personJSON.get(USER_FAMILY_MEMBERS);
            ArrayList<String> familyMembers = new ArrayList<>();
            if(familyJSON != null){
              for(Object member : familyJSON){
                familyMembers.add((String) member);
                }
            }
            
            users.add(new User(username, firstName, lastName, null, phoneNumber, address, password, familyMembers));

        }

    }   catch (Exception e){
        e.printStackTrace();
    }
    return users;
    }

    public static ArrayList<Shelter> shelters = new ArrayList();

}
