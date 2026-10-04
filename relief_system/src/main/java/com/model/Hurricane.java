package com.model;

public class Hurricane {
    
    private int category;
    private int windSpeed;
    private Location location;
    private String expectedConditions;
    private String name;
    private double size;
    private boolean isActive = true;

    public Hurricane(int category, int windSpeed, Location location, String conditions,
        String name, double size){
            this.category = category;
            this.windSpeed = windSpeed;
            this.location = location;
            this.expectedConditions = conditions;
            this.name = name;
            this.size = size;
    }

    public void getUpdates(){

    }

    public void trackPath(){
        
    }
}