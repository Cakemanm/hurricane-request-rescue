package com.model;

public enum RequestType {
    BRUISED("Bruised",3),
    BLEEDING("Bleeding",6),
    BROKEN_BONE("Broken bone",10),
    FLAT_TIRE("Flat tire",3),
    NO_POWER("No power",3),
    BROKEN_DOWN_CAR("Broken down car",2),
    HVAC_ISSUE("HVAC issue",3),
    NO_SHELTER("No Shelter",9),
    NO_TRANSLATOR("No translator",7),
    FALLEN_TREE("Fallen tree",1),
    ROADBLOCK("Roadblock",2),
    FLOOD("Flood",6),
    LANDSLIDE("Landslide",7),
    RESOURCE("Resource",5),
    TRANSPORATION("Transporation", 8),
    MEDICAL_ASSISTANCE("Medical Assistance", 9);

    public final String label;
    public final int urgency;
    private RequestType (String label, int urgency){
        
        this.label = label;
        this.urgency = urgency;
    }
}
