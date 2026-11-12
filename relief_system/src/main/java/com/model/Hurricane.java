package com.model;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A hurricane object listing its important details such as strength,
 * zip codes of affected areas, and expected conditions
 * 
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */
public class Hurricane {
    
    private UUID id;
    private String name;
    private int category;
    private int windSpeed;
    private ArrayList<Integer> affectedAreas;
    private String expectedConditions;
    private double size;
    private boolean isActive;    

    /**
     * Creates a hurricane
     * @param id the hurricane's unique identifier
     * @param name the hurricane's name
     * @param category the category level of the hurricane (1-5)
     * @param windSpeed the max wind speed in MPH
     * @param affectedAreas the zipcodes in its path
     * @param conditions the condtions people should expect
     * @param size the diameter of the storm in miles
     * @param active whether the storm is still active
     */
    public Hurricane(UUID id, String name, int category, int windSpeed, ArrayList<Integer> affectedAreas,
        String conditions, double size, boolean active) {
            this.id = id;
            this.category = category;
            this.windSpeed = windSpeed;
            this.affectedAreas = affectedAreas;
            this.expectedConditions = conditions;
            this.name = name;
            this.size = size;
            this.isActive = active;
    }

    /**
     * Returns hurricane's unique identifier
     * @return the ID
     */
    public UUID getId(){
        return this.id;
    }

    /**
     * Returns hurricane's name
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the category level 
     * @return the category, 1-5
     */
    public int getCategory() {
        return this.category;
    }

    /**
     * Returns max wnd speed
     * @return the wind speed in MPH
     */
    public int getWindSpeed() {
        return this.windSpeed;
    }

    /**
     * Returns zip codes in hurricane's path
     * @return the affected zip codes
     */
    public ArrayList<Integer> getAffectedAreas() {
        return this.affectedAreas;
    }

    /**
     * Returns conditions people should expect
     * @return the expected conditions
     */
    public String getConditions() {
        return this.expectedConditions;
    }

    /**
     * Returns storm's diameter
     * @return the size in miles
     */
    public double getSize() {
        return this.size;
    }

    /**
     * Returns wheather storm is still active
     * @return true if hurricane is active
     */
    public boolean getActive() {
        return this.isActive;
    }
}