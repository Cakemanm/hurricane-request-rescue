package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class LocationList {
    private static LocationList locationList;
    private ArrayList<Location> locations;
    private LocationList(){};

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
            if(location.getId() == id)
                return location;
        }
        return null;
    }

}
