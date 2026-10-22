package com.model;

public class Resource {
    
    private ResourceType resourceType;
    private int quantity;

    public Resource(ResourceType resourceType, int quantity){
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void updateQuantity(int amount){
        this.quantity = amount;
    }

    public String toString(){
        return this.resourceType.label + ", Quantity: " + this.quantity;
    }
}
