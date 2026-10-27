package com.model;

/**
 * DataConstants
 * Constants to be used by the DataLoader and DataWriter
 * 
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */
public class DataConstants {
    //User constants
    protected static final String USER_FILE_NAME = "json\\Users.json";
    protected static final String USER_ID = "id";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_AGE = "age";
    protected static final String USER_PHONE_NUMBER = "phoneNumber";
    protected static final String USER_ADDRESS = "address";
    protected static final String USER_ADDRESS_STREET = "street";
    protected static final String USER_ADDRESS_CITY = "city";
    protected static final String USER_ADDRESS_STATE = "state";
    protected static final String USER_ADDRESS_ZIPCODE = "zipcode";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_SHELTER = "shelter";
    protected static final String USER_SKILLS = "skills";
    protected static final String USER_HAS_PET = "hasPet";
    protected static final String USER_PET_DESCRIPTION = "petDescription";
    protected static final String USER_FAMILY_MEMBERS = "familyMembers";
    protected static final String FAMILY_MEMBER_NAME = "name";
    protected static final String FAMILY_MEMBER_AGE = "age";

    //Shelter constants
    protected static final String SHELTER_FILE_NAME = "json\\Shelters.json";
    protected static final String SHELTER_ID = "id";
    protected static final String SHELTER_NAME = "name";
    protected static final String SHELTER_AVAILABILITY = "availability";
    protected static final String SHELTER_RESOURCES = "resources";
    protected static final String SHELTER_RESOURCES_QUANTITY = "quantity";
    protected static final String SHELTER_RESOURCES_TYPE = "type";
    protected static final String SHELTER_ADDRESS = "address";
    protected static final String SHELTER_ADDRESS_STREET = "street";
    protected static final String SHELTER_ADDRESS_CITY = "city";
    protected static final String SHELTER_ADDRESS_STATE = "state";
    protected static final String SHELTER_ADDRESS_ZIPCODE = "zipcode";
    protected static final String SHELTER_SHELTER_TYPES = "shelterTypes";
    protected static final String SHELTER_OCCUPANCY = "occupancy";
    protected static final String SHELTER_CAPACITY = "capacity";
    protected static final String SHELTER_PET_CAPACITY = "petCapacity";
    protected static final String SHELTER_PET_COUNT = "petCount";
    protected static final String SHELTER_RESOURCE_TYPE = "type";
    protected static final String SHELTER_RESOURCE_QUANTITY = "quantity";

    //Request constants
    protected static final String REQUEST_FILE_NAME = "json\\Requests.json";
    protected static final String REQUEST_PRIORITY = "requestPriority";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_ADDRESS = "address";
    protected static final String REQUEST_ADDRESS_STREET = "street";
    protected static final String REQUEST_ADDRESS_CITY = "city";
    protected static final String REQUEST_ADDRESS_STATE = "state";
    protected static final String REQUEST_ADDRESS_ZIPCODE = "zipcode";
    protected static final String REQUEST_REPORTED_URGENCY = "reportedUrgency";
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
    protected static final String LOCATION_ADDRESS = "address";
    protected static final String LOCATION_LATITUDE = "latitude";
    protected static final String LOCATION_LONGITUDE = "longitude";

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

    public static boolean isJUnitTest() {  
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
		  if (element.getClassName().startsWith("org.junit.")) {
			return true;
		  }           
		}
		return false;
	  }
}
