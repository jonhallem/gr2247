package BikeRentalApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;


public class Bike {
    
    //Tilstand
    private String iD;
    private String type;
    private String colour;
    private final Collection<String> validTypes = new ArrayList<>(Arrays.asList("Landeveissykkel", "Terrengsykkel", "Elekstrisk terrengsykkel", "Fjellsykkel", "Fatbike", "Elektrisk fatbike", "Tandemsykkel", "Elektrisk tandemsykkel", "Christianiasykkel", "Hybridsykkel"));
    private final Collection<String> validColours = new ArrayList<>(Arrays.asList("Rød", "Blå", "Grønn", "Rosa", "Gul", "Lilla", "Svart", "Hvit"));

    // Konstruktør
    public Bike(String iD, String type, String colour) {
        this.validateID(iD);
        this.validateType(type);
        this.validateColour(colour);
        this.iD = iD;
        this.type = type;
        this.colour = colour;
    }


    //Valideringsmetoder

    private void validateID(String iD) {
        this.inputNotNullValidator(iD);
        if (!Pattern.matches("[A-Z0-9]{8}", iD)) {
            throw new IllegalArgumentException("ID-format ugyldig. Må bestå av tall og store bokstaver og ha en lengde på 8 tegn.");
        }
    }

    private void validateType(String type) {
        this.inputNotNullValidator(type);
        if (!this.validTypes.contains(type)) {
            throw new IllegalArgumentException("Type-input ugyldig.");
        }
    }

    private void validateColour(String colour) {
        this.inputNotNullValidator(colour);
        if (!this.validColours.contains(colour)) {
            throw new IllegalArgumentException("Farge-input ugyldig.");
        }
    }

    private void inputNotNullValidator(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Input kan ikke være null");
        }
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

    public static void main(String[] args) {
        Bike bike = new Bike(null, null, null);
    }
    
}
