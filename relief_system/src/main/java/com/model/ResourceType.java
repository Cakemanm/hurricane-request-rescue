package com.model;

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

    public final String label;
    private ResourceType(String label){
        this.label = label;
    }
}
