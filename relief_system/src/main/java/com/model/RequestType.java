package com.model;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * 
 * Defines the types of assistance that can be requested.
 *
 * <p>Each type has a display label, an urgency value used to calculate
 * request priority, and a skill used to match requests with responders.</p>
 */
public enum RequestType {
    BRUISED("Bruised",3,Skill.CAN_PROVIDE_MEDICAL_ASSISTANCE),
    BLEEDING("Bleeding",6,Skill.CAN_PROVIDE_MEDICAL_ASSISTANCE),
    BROKEN_BONE("Broken bone",10,Skill.CAN_PROVIDE_MEDICAL_ASSISTANCE),
    FLAT_TIRE("Flat tire",3,Skill.MECHANIC),
    NO_POWER("No power",3,Skill.HAS_POWER_TOOLS),
    BROKEN_DOWN_CAR("Broken down car",2,Skill.MECHANIC),
    HVAC_ISSUE("HVAC issue",3,Skill.HAS_POWER_TOOLS),
    NO_SHELTER("No Shelter",9,Skill.SHELTER_STAFF),
    NO_TRANSLATOR("No translator",7,Skill.LANGUAGE_TRANSLATOR),
    FALLEN_TREE("Fallen tree",1,Skill.HEAVY_MACHINERY_OPERATOR),
    ROADBLOCK("Roadblock",2,Skill.HEAVY_LIFTER),
    FLOOD("Flood",6,Skill.BOAT_OPERATOR),
    LANDSLIDE("Landslide",7,Skill.HEAVY_MACHINERY_OPERATOR),
    RESOURCE("Resource",5,Skill.RESOURCE_SUPPLIER),
    TRANSPORTATION("Transporation", 8,Skill.TRANSPORTATION),
    MEDICAL_ASSISTANCE("Medical Assistance", 9,Skill.CAN_PROVIDE_MEDICAL_ASSISTANCE);

    /**
     * Creates a request type with a label, urgency value, and matching skill.
     *
     * @param label the human-readable request type label
     * @param urgency the urgency value used in priority calculations
     * @param skill the skill used to identify suitable responders
     */
    public final String label;
    public final int urgency;
    public final Skill skill;
    private RequestType (String label, int urgency, Skill skill){
        
        this.label = label;
        this.urgency = urgency;
        this.skill = skill;
    }
}

