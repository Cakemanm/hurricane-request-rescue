package com.model;

/**
 * An item and its quantity, usually tied to a supply shelter
 * 
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */
public class Resource {
    
    private ResourceType resourceType;
    private int quantity;

    /**
     * Creates a resource
     * @param resourceType the kind of supplies
     * @param quantity how many of the item
     */
    public Resource(ResourceType resourceType, int quantity){
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    /**
     * Return the type of the supplies
     * @return the resource type
     */
    public ResourceType getResourceType() {
        return this.resourceType;
    }

    /**
     * Returns how many units are on hand
     * @return the quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Replaces the quanity of hand
     * @param amount the new quantity
     */
    public void updateQuantity(int amount){
        this.quantity = amount;
    }

    /**    (non-Javadoc)
     * Describe the resource
     * @return example:" Water, Quantity: 40"
     */
    public String toString(){
        return this.resourceType.label + ", Quantity: " + this.quantity;
    }
}
