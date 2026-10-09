package com.model;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    private static void writeFile(String fileName, JSONArray json) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonPrint(json.toJSONString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String jsonPrint(String json) {
        StringBuilder out = new StringBuilder();
        int indent = 0;
        boolean inString = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            }
            if (inString) {
                out.append(c);
                continue;
            }
            if (c == '{' || c == '[') {
                indent++;
                out.append(c).append('\n').append("  ".repeat(indent));
            } else if (c == '}' || c == ']') {
                indent--;
                out.append('\n').append("   ".repeat(indent)).append(c);
            } else if (c == ',') {
                out.append(c).append('\n').append(" ".repeat(indent));
            } else if (c == ':') {
                out.append(" : ");
            } else {
                out.append(c);
            }
        }
        return out.toString().replaceAll("\\[\\s+\\]", "[]");
    }

    public static void saveUsers(ArrayList<User> users) {
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
        writeFile(USER_FILE_NAME, peopleJSON);
    }

    public static void saveShelters(ArrayList<Shelter> shelters) {
        JSONArray sheltersJSON = new JSONArray();
        for (Shelter shelter : shelters) {
            JSONObject shelterJSON = new JSONObject();
            shelterJSON.put(SHELTER_ID, shelter.getId());
            shelterJSON.put(SHELTER_NAME, shelter.getName());
            shelterJSON.put(SHELTER_AVAILABILITY, shelter.getAvailability());
            shelterJSON.put(SHELTER_LOCATION, shelter.getLocation());
            shelterJSON.put(SHELTER_SHELTER_TYPE, shelter.getShelterType());
            shelterJSON.put(SHELTER_OCCUPANCY, shelter.getOccupancy());
            shelterJSON.put(SHELTER_CAPACITY, shelter.getCapacity());

            JSONArray resourceJSON = new JSONArray();
            for (Resource resource : shelter.getResources()) {
                JSONObject resourceJSON = new JSONObject();
                resourceJSON.put(SHELTER_RESOURCE_TYPE, resource.getResourceType().toString());
                resourceJSON.put(SHELTER_RESOURCE_QUANTITY, resource.getQuantity());
                resourceJSON.add(resourceJSON);
            }
            shelterJSON.put(SHELTER_RESOURCES, resourceJSON);

            shelterJSON.add(shelterJSON);
        }
        writeFile(SHELTER_FILE_NAME, sheltersJSON);
    }

    public static void saveRequests(ArrayList<Request> requests) {
        JSONArray requestsJSON = new JSONArray();
        for (Request request : requests) {
            JSONObject requestJSON = new JSONObject();
            requestJSON.put(REQUEST_ID, request.getId());
            requestJSON.put(REQUEST_PRIORITY, request.getRequestPrioity());
            requestJSON.put(REQUEST_STATUS, request.getStatus());
            requestJSON.put(REQUEST_LOCATION, request.getLocation());
            requestJSON.put(REQUEST_REQUESTER, request.getRequester());
            requestJSON.put(REQUEST_RESPONDER, request.getResponder());

            JSONArray commentsJSON = new JSONArray();
            for (String comment : request.getComments()) {
                commentsJSON.add(comment);
            }
            requestJSON.put(REQUEST_COMMENT, commentsJSON);

            JSONArray typesJSON = new JSONArray();
            for (RequestType type : request.getRequestTypes()) {
                typesJSON.add(type.toString());
            }
            requestJSON.put(REQUEST_REQUEST_TYPES, typesJSON);

            requestsJSON.add(requestJSON);
        }
        writeFile(REQUEST_FILE_NAME, requestsJSON);
    }

    public static void saveHurricane(ArrayList<Hurricane> hurricanes) {
        JSONArray hurricanesJSON = new JSONArray();
        for (Hurricane hurricane : hurricanes) {
            JSONObject hurricaneJSON = new JSONObject();
            hurricanesJSON.put(HURRICANE_NAME, hurricane.getName());
            hurricanesJSON.put(HURRICANE_CATEGORY, hurricane.getCategory());
            hurricanesJSON.put(HURRICANE_WINDSPEED, hurricane.getWindSpeed());
            hurricanesJSON.put(HURRICANE_LOCATION, hurricane.getLocation());
            hurricanesJSON.put(HURRICANE_CONDITIONS, hurricane.getCoditions());
            hurricanesJSON.put(HURRICANE_SIZE, hurricane.getSize());
            hurricanesJSON.put(HURRICANE_ACTIVE, hurricane.getActive());
            hurricanesJSON.add(hurricaneJSON);
        }
        writeFile(HURRICANE_FILE_NAME, hurricanesJSON);
    }
}
