package com.model;

public class FamilyMember {
    String name;
    int age;
    public FamilyMember(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String toString(){
        return this.name + ", " + this.age;
    }
}
