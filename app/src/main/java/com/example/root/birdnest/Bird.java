package com.example.root.birdnest;


public class Bird {

    public int id;
    public String ScientificName, FirstName, FamilyName, LastName, LocalName, MalayName, Location, Image, Sound;

    public Bird(){}

    // Bird Overload
    public Bird(String SciName, String FirstN, String LastN, String LocalN, String FamilyN, String location, String Img, String Sound ){
        super();
        this.ScientificName = SciName;
        this.FirstName = FirstN;
        this.FamilyName = FamilyN;
        this.LocalName = LocalN;
        this.LastName = LastN;
        this.FirstName = FirstN;
        this.Location =location;
        this.Image = Img;
        this.Sound = Sound;

    }

    // Update Methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String ScientificName){
        this.ScientificName = ScientificName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setFirstName(String firstName){
        this.FirstName = FirstName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public void setMalayName(String malayName) {
        MalayName = malayName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }


    public void setLocalName(String localName) {
        LocalName = localName;
    }

    public void setLocation(String location){
        this.Location = location;
    }
    public void setImage(String image){
        this.Image = image;
    }
    public void setSound(String sound){
        this.Sound = sound;
    }

    @Override

    // For Logging Purpose
    public String toString() {
        return "Bird [id]:" + this.id+
                " [Sci]: "+this.ScientificName+
                " [FN]: " + this.FamilyName +
                " [LN]: " + this.LocalName +
                " [MN]: " + this.MalayName+
                " [Family]" + this.FamilyName+
                " [Image]"+ this.Image+
                " [Sound]"+ this.Sound;
    }
}
