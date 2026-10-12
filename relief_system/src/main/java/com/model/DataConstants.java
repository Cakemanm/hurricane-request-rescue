package com.model;

public class DataConstants {
    //User constants
    protected static final String USER_FILE_NAME = "json\\Users.json";
    protected static final String USER_ID = "id";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_LOCATION = "location";
    protected static final String USER_PHONE_NUMBER = "phoneNumber";
    protected static final String USER_ADDRESS = "address";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_SHELTER = "shelter";
    protected static final String USER_SKILLS = "skills";
    protected static final String USER_HAS_PET = "hasPet";
    protected static final String USER_FAMILY_MEMBERS = "familyMembers";


    //Shelter constants
    protected static final String SHELTER_FILE_NAME = "json\\Shelters.json";
    protected static final String SHELTER_ID = "id";
    protected static final String SHELTER_NAME = "name";
    protected static final String SHELTER_AVAILABILITY = "availability";
    protected static final String SHELTER_RESOURCES = "resources";
    protected static final String SHELTER_LOCATION = "location";
    protected static final String SHELTER_SHELTER_TYPES = "shelterType";
    protected static final String SHELTER_OCCUPANCY = "occupancy";
    protected static final String SHELTER_CAPACITY = "capacity";

    //Request constants
    protected static final String REQUEST_FILE_NAME = "json\\Requests.json";
    protected static final String REQUEST_PRIORITY = "requestPriority";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_LOCATION = "location";
    protected static final String REQUEST_COMMENTS = "comments";
    protected static final String REQUEST_REQUEST_TYPES = "requestTypes";
    protected static final String REQUEST_REQUESTER = "requester";
    protected static final String REQUEST_RESPONDER = "responder";
    protected static final String REQUEST_ID = "id";

    //Location constants
    protected static final String LOCATION_FILE_NAME = "json\\Locations.json";
    protected static final String LOCATION_ID = "id";
    protected static final String LOCATION_STATE = "state";
    protected static final String LOCATION_CITY = "city";
    protected static final String LOCATION_ZIPCODE = "zipcode";

    //Hurricane constants
    protected static final String HURRICANE_FILE_NAME = "json\\Hurricane.json";
    protected static final String HURRICANE_ID = "id";
    protected static final String HURRICANE_NAME = "name";
    protected static final String HURRICANE_CATEGORY = "category";
    protected static final String HURRICANE_WIND_SPEED = "windSpeed";
    protected static final String HURRICANE_AFFECTED_AREAS = "affectedAreas";
    protected static final String HURRICANE_EXPECTED_CONDITIONS = "expectedConditions";
    protected static final String HURRICANE_SIZE = "size";
    protected static final String HURRICANE_IS_ACTIVE = "isActive";

}
