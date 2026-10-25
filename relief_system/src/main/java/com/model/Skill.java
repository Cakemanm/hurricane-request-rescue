package com.model;

public enum Skill {
    CAN_COOK("Can cook"),
    HAS_POWER_TOOLS("Has power tools"),
    CAN_DRIVE("Can drive"),
    SHELTER_STAFF("Shelter staff"),
    BOAT_OPERATOR("Boat operator"),
    HEAVY_MACHINERY_OPERATOR("Heavy machinery operator"),
    HEAVY_LIFTER("Heavy lifter"),
    CAN_PROVIDE_MEDICAL_ASSISTANCE("Medical assistance"),
    LANGUAGE_TRANSLATOR("Language translator"),
    TRANSPORTATION("Transportation"),
    MECHANIC("Mechanic"),
    RESOURCE_SUPPLIER("Resource supplier");

    public final String label;
    private Skill(String label){
        this.label = label;
    }
}
