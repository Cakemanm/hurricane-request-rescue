package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Hurricane {
    
    private UUID id;
    private String name;
    private int category;
    private int windSpeed;
    private ArrayList<Integer> affectedAreas;
    private String expectedConditions;
    private double size;
    private boolean isActive;
    private Location location;
    

    public Hurricane(UUID id, String name, int category, int windSpeed, ArrayList<Integer> affectedAreas,
        String conditions, double size, boolean active, Location location) {
            this.id = id;
            this.category = category;
            this.windSpeed = windSpeed;
            this.affectedAreas = affectedAreas;
            this.expectedConditions = conditions;
            this.name = name;
            this.size = size;
            this.isActive = active;
            this.location = location;
    }

    public UUID getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCategory() {
        return this.category;
    }

    public int getWindSpeed() {
        return this.windSpeed;
    }

    public ArrayList<Integer> getAffectedAreas() {
        return this.affectedAreas;
    }

    public String getConditions() {
        return this.expectedConditions;
    }

    public Location getLocation() {
        return this.location;
    }

    public double getSize() {
        return this.size;
    }

    public boolean getActive() {
        return this.isActive;
    }
}