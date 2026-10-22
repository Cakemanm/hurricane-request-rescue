package com.model;

import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    private static final String INDENT = "      ";

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

    private static String idOf(Object id) {
        return id == null ? null : id.toString();
    }

    public static void saveUsers(ArrayList<User> users) {
        JSONArray peopleJSON = new JSONArray();
        for(User user : users){
            JSONObject personJSON = new JSONObject();
            personJSON.put(USER_ID, user.getID().toString());
            personJSON.put(USER_USERNAME, user.getUsername());
            personJSON.put(USER_FIRST_NAME, user.getFirstName());
            personJSON.put(USER_LAST_NAME, user.getLastName());
            personJSON.put(USER_AGE, user.getAge());
            personJSON.put(USER_LOCATION, user.getLocation() == null ? null : idOf(user.getLocation().getId()));
            personJSON.put(USER_PHONE_NUMBER, user.getPhoneNumber());
            personJSON.put(USER_ADDRESS, user.getAddress());
            personJSON.put(USER_PASSWORD, user.getPassword());
            personJSON.put(USER_SHELTER, user.getShelter() == null ? null : idOf(user.getShelter().getId()));
            personJSON.put(USER_HAS_PET, user.hasPet());
            personJSON.put(USER_PET_DESCRIPTION, user.getPetDescription());

            JSONArray skillsJSON = new JSONArray();
            for(Skill skill : user.getSkills()){
                skillsJSON.add(skill.toString());
            }
            personJSON.put(USER_SKILLS, skillsJSON);

            JSONArray familyJSON = new JSONArray();
            for(FamilyMember member : user.getFamilyMembers()){
                JSONObject memberJSON = new JSONObject();
                memberJSON.put(FAMILY_MEMBER_NAME, member.getName());
                memberJSON.put(FAMILY_MEMBER_AGE, member.getAge());
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
            shelterJSON.put(SHELTER_ID, idOf(shelter.getId()));
            shelterJSON.put(SHELTER_NAME, shelter.getName());
            shelterJSON.put(SHELTER_AVAILABILITY, shelter.getAvailability(shelter.getOccupancy(), shelter.getCapacity()));
            shelterJSON.put(SHELTER_LOCATION, shelter.getLocation() == null ? null : idOf(shelter.getLocation().getId()));
            shelterJSON.put(SHELTER_OCCUPANCY, shelter.getOccupancy());
            shelterJSON.put(SHELTER_CAPACITY, shelter.getCapacity());
            shelterJSON.put(SHELTER_PET_COUNT, shelter.getPetCount());
            shelterJSON.put(SHELTER_PET_CAPACITY, shelter.getPetCapacity());

            JSONArray typesJSON = new JSONArray();
            for (ShelterType type : shelter.getShelterTypes()) {
                typesJSON.add(type.toString());
            }
            shelterJSON.put(SHELTER_SHELTER_TYPES, typesJSON);

            JSONArray resourcesJSON = new JSONArray();
            for (Resource resource : shelter.getResources()) {
                JSONObject resourceJSON = new JSONObject();
                resourceJSON.put(SHELTER_RESOURCE_TYPE, resource.getResourceType().toString());
                resourceJSON.put(SHELTER_RESOURCE_QUANTITY, resource.getQuantity());
                resourcesJSON.add(resourceJSON);
            }
            shelterJSON.put(SHELTER_RESOURCES, resourcesJSON);

            sheltersJSON.add(shelterJSON);
        }
        writeFile(SHELTER_FILE_NAME, sheltersJSON);
    }

    public static void saveRequests(ArrayList<Request> requests) {
        JSONArray requestsJSON = new JSONArray();
        for (Request request : requests) {
            JSONObject requestJSON = new JSONObject();
            requestJSON.put(REQUEST_ID, idOf(request.getId()));
            requestJSON.put(REQUEST_PRIORITY, request.getRequestPriority());
            requestJSON.put(REQUEST_STATUS, request.getStatus().name());
            requestJSON.put(REQUEST_LOCATION, request.getLocation() == null ? null : idOf(request.getLocation().getId()));
            requestJSON.put(REQUEST_ADDRESS, request.getAddress());
            requestJSON.put(REQUEST_REPORTED_URGENCY, request.getReportedUrgency());
            requestJSON.put(REQUEST_REQUESTER, request.getRequester() == null ? null : idOf(request.getRequester().getID()));
            requestJSON.put(REQUEST_RESPONDER, request.getResponder() == null ? null : idOf(request.getResponder().getID()));

            JSONArray commentsJSON = new JSONArray();
            for (String comment : request.getComments()) {
                commentsJSON.add(comment);
            }
            requestJSON.put(REQUEST_COMMENTS, commentsJSON);

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
            hurricaneJSON.put(HURRICANE_ID, idOf(hurricane.getId()));
            hurricaneJSON.put(HURRICANE_NAME, hurricane.getName());
            hurricaneJSON.put(HURRICANE_CATEGORY, hurricane.getCategory());
            hurricaneJSON.put(HURRICANE_WIND_SPEED, hurricane.getWindSpeed());
            JSONArray areasJSON = new JSONArray();
            areasJSON.addAll(hurricane.getAffectedAreas());
            hurricaneJSON.put(HURRICANE_AFFECTED_AREAS, areasJSON);
            hurricaneJSON.put(HURRICANE_EXPECTED_CONDITIONS, hurricane.getConditions());
            hurricaneJSON.put(HURRICANE_SIZE, hurricane.getSize());
            hurricaneJSON.put(HURRICANE_IS_ACTIVE, hurricane.getActive());
            hurricanesJSON.add(hurricaneJSON);
        }
        writeFile(HURRICANE_FILE_NAME, hurricanesJSON);
    }

    public static void saveLocations(ArrayList<Location> locations) {
        JSONArray locationsJSON = new JSONArray();
        for (Location location : locations) {
            JSONObject locationJSON = new JSONObject();
            locationJSON.put(LOCATION_ID, idOf(location.getId()));
            locationJSON.put(LOCATION_STATE, location.getState());
            locationJSON.put(LOCATION_CITY, location.getCity());
            locationJSON.put(LOCATION_ZIPCODE, location.getZipcode());
            locationJSON.put(LOCATION_ADDRESS, location.getAddress());
            locationJSON.put(LOCATION_LATITUDE, location.getLatitude());
            locationJSON.put(LOCATION_LONGITUDE, location.getLongitude());
            locationsJSON.add(locationJSON);
        }
        writeFile(LOCATION_FILE_NAME, locationsJSON);
    }
}
