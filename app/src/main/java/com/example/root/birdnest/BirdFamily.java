package com.example.root.birdnest;

/**
 * Created by root on 11/15/14.
 */
public class BirdFamily {
    public String FamilyName;
    public int id;

    // Overload
    public BirdFamily(){}
    public BirdFamily(String FName){this.FamilyName=FName;}

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Family [id]:" + this.id+
                " [FN]: "+this.FamilyName;
    }
}
