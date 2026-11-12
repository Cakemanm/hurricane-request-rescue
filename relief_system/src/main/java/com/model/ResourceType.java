package com.model;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * ResourceType
 * Types for resources
 */
public enum ResourceType {
    FIREWOOD("Firewood"),
    WATER("Water"),
    FOOD("Food"),
    GASOLINE("Gasoline"),
    CLOTHES("Clothes"),
    CHARGERS("Chargers"),
    FIRST_AID("First aid"),
    BLANKETS("Blankets"),
    BANDAGES("Bandages"),
    MEDICINE("Medicine"),
    OPEN_BATHROOMS("Open bathrooms");

    /**
     * A constructor with human-readable labels
     */
    public final String label;
    private ResourceType(String label){
        this.label = label;
    }
}
