package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    public static ArrayList<User> users = new ArrayList<User>();

    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();
    try{
        FileReader reader = new FileReader(USER_FILE_NAME);
        JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

        for(int i = 0; i < peopleJSON.size(); i++){
            JSONObject personJSON = (JSONObject)peopleJSON.get(i);

            UUID id = UUID.fromString((String)personJSON.get(USER_ID));
            String username = (String)personJSON.get(USER_USERNAME);
            String firstName = (String)personJSON.get(USER_FIRST_NAME);
            String lastName = (String)personJSON.get(USER_LAST_NAME);
            String phoneNumber = (String)personJSON.get(USER_PHONE_NUMBER);
            String address = (String)personJSON.get(USER_ADDRESS);
            String password = (String)personJSON.get(USER_PASSWORD);
            boolean hasPet = (boolean)personJSON.get(USER_HAS_PET);

            UUID locationId = UUID.fromString((String)personJSON.get(USER_LOCATION));
            Location location = (locationId == null ? null : LocationList.getInstance().getLocationById(locationId));
            
            String shelterIdString = (String) personJSON.get(USER_SHELTER);

            UUID shelterId = shelterIdString == null ? null : UUID.fromString(shelterIdString);

            Shelter shelter = shelterId == null ? null : ShelterList.getInstance().getShelterById(shelterId);

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
            
            users.add(new User(username, firstName, lastName, location, phoneNumber, address, password, familyMembers));

        }

    }   catch (Exception e){
        e.printStackTrace();
    }
    return users;
    }

    public static ArrayList<Shelter> shelters = new ArrayList();
    public static ArrayList<Shelter> getShelters(){
        try{
            FileReader reader = new FileReader(SHELTER_FILE_NAME);
            JSONArray placesJSON = (JSONArray) new JSONParser().parse(reader);
            for(int i = 0; i < placesJSON.size(); i++){
                JSONObject placeJSON = (JSONObject)placesJSON.get(i);

                UUID id = UUID.fromString((String)placeJSON.get(SHELTER_ID));
                String name = (String)placeJSON.get(SHELTER_NAME);
                Boolean availability = (Boolean)placeJSON.get(SHELTER_AVAILABILITY);
                int occupancy = (int)placeJSON.get(SHELTER_OCCUPANCY);
                int capacity = (int)placeJSON.get(SHELTER_CAPACITY);

                UUID locationId = UUID.fromString((String)placeJSON.get(SHELTER_LOCATION));
                
                Location location = (locationId == null ? null : LocationList.getInstance().getLocationById(locationId));

                JSONArray shelterTypesJSON = (JSONArray)placeJSON.get(SHELTER_SHELTER_TYPES);
                ArrayList<ShelterType> shelterTypes = new ArrayList<>();
                for(Object type : shelterTypesJSON){
                    shelterTypes.add((ShelterType) type);
                }

                JSONArray resourcesJSON = (JSONArray)placeJSON.get(SHELTER_RESOURCES);
                ArrayList<Resource> resources = new ArrayList<>();
                if(resourcesJSON != null){
                    for(Object resource : resourcesJSON)
                        resources.add((Resource) resource);
                }

                shelters.add(new Shelter(name, availability, location, resources, shelterTypes, occupancy, capacity, id));
            }


        }   catch (Exception e){
        e.printStackTrace();
        }
        return shelters;
    }


    public static ArrayList<Location> locations = new ArrayList();
    public static ArrayList<Location> getLocations(){
        try{
            FileReader reader = new FileReader(LOCATION_FILE_NAME);
            JSONArray placesJSON = (JSONArray) new JSONParser().parse(reader);
            for(int i = 0; i < placesJSON.size(); i++){
                JSONObject placeJSON = (JSONObject)placesJSON.get(i);
                UUID id = UUID.fromString((String)placeJSON.get(LOCATION_ID));
                String state = (String)placeJSON.get(LOCATION_STATE);
                String city = (String)placeJSON.get(LOCATION_CITY);
                int zipcode = (int)placeJSON.get(LOCATION_ZIPCODE);

                locations.add(new Location(id, state, city, zipcode));
            }
        
        }   catch (Exception e){
        e.printStackTrace();
        }
        return locations;

    }


}
