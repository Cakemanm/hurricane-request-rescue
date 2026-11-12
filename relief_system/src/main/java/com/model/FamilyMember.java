package com.model;

/**
 * A member of a user's household  that does not have an account
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 */
public class FamilyMember {
    String name;
    int age;

    /**
     * Creates a family member
     * @param name the family member's full name
     * @param age the family member's age in years
     */
    public FamilyMember(String name, int age){
        this.name = name;
        this.age = age;
    }

    /**
     * Returns the family member's full name
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the family member's age
     * @return the age in years
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Describes the family member
     * @reutrn example: "Sophie Carter, 9"
     */
    public String toString(){
        return this.name + ", " + this.age;
    }
}
