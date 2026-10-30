package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton that holds every hurricane the system is tracking
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */
public class HurricaneHandler {
    private static HurricaneHandler hurricaneHandler;
    private ArrayList<Hurricane> hurricanes;

    /**
     * Loads the hurricane using the {@link DataLoader}
     */
    private HurricaneHandler(){
        hurricanes = DataLoader.getHurricanes();
    }

    /**
     * Returns the shared hurricane handler and creating it upon first access
     * @return the singleton hurricane handler
     */
    public static HurricaneHandler getInstance(){
        if(hurricaneHandler == null)
            hurricaneHandler = new HurricaneHandler();
        return hurricaneHandler;
    }

    /**
     * Returns every hurricane being tracked
     * @return the list of hurricanes
     */
    public ArrayList<Hurricane> getHurricanes() {
        return hurricanes;
    }

    /**
     * find a hurricane by name
     * @param name the hurricane's name
     * @return the matching hurricane, or null if nothing is found
     */
    public Hurricane getHurricane(String name){
        for(Hurricane hurricane : hurricanes){
            if(name.equalsIgnoreCase(hurricane.getName()))
                return hurricane;
        }
        return null;
    }

    /**
     * Starts tracking a new hurricane
     * @param id the hurricane's unique ID
     * @param name the hurricane's name
     * @param category the category 1-5
     * @param windSpeed the max wind speed in MPH
     * @param affectedAreas the zip codes in its path
     * @param conditions tbe conditions people should expect
     * @param size the storm's diameter in miles
     * @param active whether the storm is active or not
     */
    public void addHurricane(UUID id, String name, int category, int windSpeed, ArrayList<Integer> affectedAreas,
        String conditions, double size, boolean active){
            hurricanes.add(new Hurricane(id, name, category, windSpeed, affectedAreas, conditions, size, active));
        }

}
