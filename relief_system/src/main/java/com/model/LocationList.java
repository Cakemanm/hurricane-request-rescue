package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class LocationList {
    private static LocationList locationList;
    private ArrayList<Location> locations;
    private LocationList(){
        locations = DataLoader.getLocations();
    };

    public static LocationList getInstance(){
        if(locationList == null)
            locationList  = new LocationList();
        return locationList;
    }

    public void addLocation(UUID id, String state, String city, int zipcode){
        locations.add(new Location(id, state, city, zipcode));
    }

    public Location getLocationById(UUID id){
        for(Location location : locations){
            if(location.getId().equals(id))
                return location;
        }
        return null;
    }

    public void addLocation(Location location){
        locations.add(location);
    }


    //matches a state, city, and zipcode combination to an existing location or creates a new one if there's no match
    public Location locationUnknownID(String state, String city, int zipcode){
        LocationList locations = LocationList.getInstance();
        for(Location l : locations.getLocations()){
            if(l.getState().equalsIgnoreCase(state) && l.getCity().equalsIgnoreCase(city) && l.getZipcode() == zipcode)
                return l;
        }
        Location location = new Location(UUID.randomUUID(), state, city, zipcode);
        locations.addLocation(location);
        return location;
    }

    public ArrayList<Location> getLocations(){
        return locations;
    }

    

}
