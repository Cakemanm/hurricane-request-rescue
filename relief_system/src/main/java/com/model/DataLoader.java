package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.location.Address;

public class DataLoader extends DataConstants {

    public static ArrayList<User> getUsers(){
    ArrayList<User> users = new ArrayList<User>();
    try{
        FileReader reader = new FileReader(USER_FILE_NAME);
        JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

        for(int i = 0; i < peopleJSON.size(); i++){
            JSONObject personJSON = (JSONObject)peopleJSON.get(i);

            String idString = (String)personJSON.get(USER_ID);
            UUID userId = idString == null ? null : UUID.fromString((String)personJSON.get(USER_ID));

            String username = (String)personJSON.get(USER_USERNAME);
            String firstName = (String)personJSON.get(USER_FIRST_NAME);
            String lastName = (String)personJSON.get(USER_LAST_NAME);
            String phoneNumber = (String)personJSON.get(USER_PHONE_NUMBER);
            String password = (String)personJSON.get(USER_PASSWORD);

            JSONObject addressJSON = (JSONObject) personJSON.get(USER_ADDRESS);
            String street = (String)addressJSON.get(USER_ADDRESS_STREET);
            String city = (String)addressJSON.get(USER_ADDRESS_CITY);
            String state = (String)addressJSON.get(USER_ADDRESS_STATE);
            String zipcode = (String)addressJSON.get(USER_ADDRESS_ZIPCODE);
            Address address = new Address(street,city,state,zipcode);

            String shelterIdString = (String) personJSON.get(USER_SHELTER);

            UUID shelterId = shelterIdString == null ? null : UUID.fromString(shelterIdString);

            Shelter shelter = shelterId == null ? null : ShelterList.getInstance().getShelterById(shelterId);

            JSONArray skillsJSON = (JSONArray)personJSON.get(USER_SKILLS);
            ArrayList<Skill> skills = new ArrayList<>();
            for(Object skill : skillsJSON){
                skills.add(Skill.valueOf((String)skill));
            }

            JSONArray familyJSON = (JSONArray)personJSON.get(USER_FAMILY_MEMBERS);
            ArrayList<FamilyMember> familyMembers = new ArrayList<>();
            if(familyJSON != null){
                for(Object member : familyJSON){
                    JSONObject memberJSON = (JSONObject) member;
                    String name = (String)memberJSON.get(FAMILY_MEMBER_NAME);
                    int age = Integer.parseInt(memberJSON.get(FAMILY_MEMBER_AGE).toString());
                    familyMembers.add(new FamilyMember(name, age));
                }
            }

            Boolean hasPet = (Boolean)personJSON.get(USER_HAS_PET);
            int age = Integer.parseInt(personJSON.get(USER_AGE).toString());
            
            users.add(new User(userId, username, firstName, lastName, phoneNumber, address, password, shelter, familyMembers, skills, hasPet, age));

        }

    }catch (Exception e) {
        throw new IllegalStateException( "Could not load users; refusing to use an incomplete list.", e);
    }
    return users;
    }

    public static ArrayList<Shelter> getShelters(){             
        ArrayList<Shelter> shelters = new ArrayList();
        try{
            FileReader reader = new FileReader(SHELTER_FILE_NAME);
            JSONArray placesJSON = (JSONArray) new JSONParser().parse(reader);
            for(int i = 0; i < placesJSON.size(); i++){
                JSONObject placeJSON = (JSONObject)placesJSON.get(i);

                UUID id = UUID.fromString((String)placeJSON.get(SHELTER_ID));
                String name = (String)placeJSON.get(SHELTER_NAME);
                Boolean availability = (Boolean)placeJSON.get(SHELTER_AVAILABILITY);
                int occupancy = Integer.parseInt(placeJSON.get(SHELTER_OCCUPANCY).toString());
                int capacity = Integer.parseInt(placeJSON.get(SHELTER_CAPACITY).toString());

                JSONObject addressJSON = (JSONObject) placeJSON.get(SHELTER_ADDRESS);
                String street = (String)addressJSON.get(SHELTER_ADDRESS_STREET);
                String city = (String)addressJSON.get(SHELTER_ADDRESS_CITY);
                String state = (String)addressJSON.get(SHELTER_ADDRESS_STATE);
                String zipcode = (String)addressJSON.get(SHELTER_ADDRESS_ZIPCODE);
                Address address = new Address(street,city,state,zipcode);

                JSONArray shelterTypesJSON = (JSONArray)placeJSON.get(SHELTER_SHELTER_TYPES);
                ArrayList<ShelterType> shelterTypes = new ArrayList<>();
                for(Object type : shelterTypesJSON){
                    shelterTypes.add(ShelterType.valueOf((String)type));
                }

                JSONArray resourcesJSON = (JSONArray)placeJSON.get(SHELTER_RESOURCES);
                ArrayList<Resource> resources = new ArrayList<>();
                for(Object resource : resourcesJSON){
                    JSONObject resourceJSON = (JSONObject) resource;
                    ResourceType type = ResourceType.valueOf((String)resourceJSON.get(SHELTER_RESOURCE_TYPE));
                    int quantity = (Integer.parseInt(resourceJSON.get(SHELTER_RESOURCE_QUANTITY).toString()));
                    resources.add(new Resource(type, quantity));
                }


    
                shelters.add(new Shelter(name, availability, resources, address, shelterTypes, occupancy, capacity, id));
            }


        }catch (Exception e) {
        throw new IllegalStateException( "Could not load shelters; refusing to use an incomplete list.", e);
        }
        return shelters;
    }

    public static ArrayList<Request> requests = new ArrayList();
    public static ArrayList<Request> getRequests(){
        try{
            FileReader reader = new FileReader(REQUEST_FILE_NAME);
            JSONArray objectsJSON = (JSONArray) new JSONParser().parse(reader);
            for(int i = 0; i < objectsJSON.size(); i++){
                JSONObject objectJSON = (JSONObject)objectsJSON.get(i);
                UUID id = UUID.fromString((String)objectJSON.get(REQUEST_ID));
                //int priority = Integer.parseInt(objectJSON.get(REQUEST_PRIORITY).toString());
                RequestStatus status = RequestStatus.valueOf((String)objectJSON.get(REQUEST_STATUS));
                int reportedUrgency = Integer.parseInt(objectJSON.get(REQUEST_REPORTED_URGENCY).toString());
                
                JSONObject addressJSON = (JSONObject) objectJSON.get(REQUEST_ADDRESS);
                String street = (String)addressJSON.get(REQUEST_ADDRESS_STREET);
                String city = (String)addressJSON.get(REQUEST_ADDRESS_CITY);
                String state = (String)addressJSON.get(REQUEST_ADDRESS_STATE);
                String zipcode = (String)addressJSON.get(REQUEST_ADDRESS_ZIPCODE);
                Address address = new Address(street,city,state,zipcode);

                
                JSONArray commentsJSON = (JSONArray)objectJSON.get(REQUEST_COMMENTS);
                ArrayList<String> comments = new ArrayList<>();
                if(commentsJSON != null){
                    for(Object comment : commentsJSON){
                        comments.add((String) comment);
                    }
                }

                JSONArray typesJSON = (JSONArray)objectJSON.get(REQUEST_REQUEST_TYPES);
                ArrayList<RequestType> types = new ArrayList<>();
                for(Object type : typesJSON)
                    types.add(RequestType.valueOf((String)type));

                UUID requesterId = UUID.fromString((String)objectJSON.get(REQUEST_REQUESTER));
                User requester = UserList.getInstance().getUserById(requesterId);

                User responder = null;
                if(objectJSON.get(REQUEST_RESPONDER) != null){
                    UUID responderId = UUID.fromString((String)objectJSON.get(REQUEST_RESPONDER));
                    responder = UserList.getInstance().getUserById(responderId);
                }
        

                requests.add(new Request(id, status, address, reportedUrgency, comments, requester, responder, types));

            }

        }   catch (Exception e){
            e.printStackTrace();
        }
        return requests;
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
                int zipcode = Integer.parseInt(placeJSON.get(LOCATION_ZIPCODE).toString());

                locations.add(new Location(id, state, city, zipcode));
            }
        
        }   catch (Exception e){
        e.printStackTrace();
        }
        return locations;

    }
    
    public static ArrayList<Hurricane> hurricanes = new ArrayList();
    public static ArrayList<Hurricane> getHurricanes(){
        try{
            FileReader reader = new FileReader(HURRICANE_FILE_NAME);
            JSONArray stormArrayJSON = (JSONArray) new JSONParser().parse(reader);
            JSONObject stormJSON = (JSONObject)stormArrayJSON.get(0);

            UUID id = UUID.fromString((String)stormJSON.get(HURRICANE_ID));
            String name = (String)stormJSON.get(HURRICANE_NAME);
            int category = Integer.parseInt(stormJSON.get(HURRICANE_CATEGORY).toString());
            int windSpeed = Integer.parseInt(stormJSON.get(HURRICANE_WIND_SPEED).toString());

            JSONArray areasJSON = (JSONArray)stormJSON.get(HURRICANE_AFFECTED_AREAS);
            ArrayList<Integer> areas = new ArrayList<>();
            for(Object zipcode : areasJSON)
                areas.add(Integer.parseInt(zipcode.toString()));

            String conditions = (String)stormJSON.get(HURRICANE_EXPECTED_CONDITIONS);
            double size = Double.parseDouble(stormJSON.get(HURRICANE_SIZE).toString());
            boolean isActive = (boolean)stormJSON.get(HURRICANE_IS_ACTIVE);

            hurricanes.add(new Hurricane(id, name, category, windSpeed, areas, conditions, size, isActive));

        }   catch (Exception e){
        e.printStackTrace();
        }
        return hurricanes;

    }


}
