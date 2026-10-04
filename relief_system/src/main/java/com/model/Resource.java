package com.model;

public class Resource {
<<<<<<< HEAD
    private ResourceType resourceType;
    private int quantity;

    public void updateQuantity(int amount){

    }

    public String toString(){
        
=======
    
    private ResourceType resourcetype;
    private int quantity;

    public Resource(ResourceType resourcetype, int quantity){
        this.resourcetype = resourcetype;
        this.quantity = quantity;
    }

    public void updateQuantity(int amount){
        this.quantity = amount;
    }

    public String toString(){
        return "Resource: " + this.resourcetype + "\tQuantity: " + this.quantity;
>>>>>>> carter-branch
    }
}
