package BikeRentalApp;

public class Bike {
    
    //Tilstand
    private String iD;
    private String type;
    private String colour;

    // Konstruktør
    public Bike(String iD, String type, String colour) {
        this.iD = iD;
        this.type = type;
        this.colour = colour;
    }


    //Gettere
    
    public String getID() {
        return this.iD;
    }

    public String getType() {
        return this.type;
    }

    public String getColour() {
        return this.colour;
    }
    
}
