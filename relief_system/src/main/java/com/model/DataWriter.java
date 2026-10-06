package com.model;

import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    public static void writeUsers(ArrayList<User> users){
        JSONArray peopleJSON = new JSONArray();
        for(User user : users){
            JSONObject personJSON = new JSONObject();
            personJSON.put(USER_ID, user.getID().toString());
            personJSON.put(USER_USERNAME, user.getUsername());
            personJSON.put(USER_FIRST_NAME, user.getFirstName());
            personJSON.put(USER_LAST_NAME, user.getLastName());
            personJSON.put(USER_LOCATION, user.getLocation());
            personJSON.put(USER_PHONE_NUMBER, user.getPhoneNumber());
            personJSON.put(USER_ADDRESS, user.getAddress());
            personJSON.put(USER_PASSWORD, user.getPassword());
            personJSON.put(USER_SHELTER, user.getShelter().toString());
            personJSON.put(USER_HAS_PET, user.hasPet());

            JSONArray skillsJSON = new JSONArray();
            for(Skill skill : user.getSkills()){
                skillsJSON.add(skill.toString());
            }
            personJSON.put(USER_SKILLS, skillsJSON);

            JSONArray familyJSON = new JSONArray();
            for(String member : user.getFamilyMembers()){
                familyJSON.add(member);
            }
            personJSON.put(USER_FAMILY_MEMBERS, familyJSON);

            peopleJSON.add(personJSON);
        }

        try{
            FileWriter writer = new FileWriter(USER_FILE_NAME);
            writer.write(peopleJSON.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
