package com.model;

public class Hurricane {
    
    private String name;
    private int category;
    private int windSpeed;
    private Location location;
    private String expectedConditions;
    private double size;
    private boolean isActive;

    public Hurricane(String name, int category, int windSpeed, Location location,
        String conditions, double size, boolean active) {
            this.category = category;
            this.windSpeed = windSpeed;
            this.location = location;
            this.expectedConditions = conditions;
            this.name = name;
            this.size = size;
            this.isActive = active;
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

    public Location getLocation() {
        return this.location;
    }

    public String getCoditions() {
        return this.expectedConditions;
    }

    public double getSize() {
        return this.size;
    }

    public boolean getActive() {
        return this.isActive;
    }
}