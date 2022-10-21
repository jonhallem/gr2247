package bikeRentalApp.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;


public class Bike {
    
    //Tilstand
    private String iD;
    private String type;
    private String colour;
    private LocalDateTime startTime;
    private final Collection<String> validTypes = new ArrayList<>(Arrays.asList(
        "Landeveissykkel", "Terrengsykkel", "Elekstrisk terrengsykkel", "Fjellsykkel", 
        "Fatbike", "Elektrisk fatbike", "Tandemsykkel", "Elektrisk tandemsykkel", 
        "Christianiasykkel", "Hybridsykkel"));
    private final Collection<String> validColours = new ArrayList<>(Arrays.asList(
        "Rød", "Blå", "Grønn", "Rosa", "Gul", "Lilla", "Svart", "Hvit"));

    // Konstruktør
    /**
     * Constructs a {@code Bike} object, used to store the ID, type and colour of a bike.
     * 
     * @param iD the String ID of the bike. Must only consist of numerals and/or capital 
     * lettrs, must be of length 8
     * @param type the String type of the bike. Must be one of the types listed in the collection 
     * "validTypes" in {@code Bike}
     * @param colour the String colour of the bike. Must be one of the colours listed in the collection 
     * "validColours" in {@code Bike}
     * @param startTime the rental start time.
     * @throws IllegalArgumentException if the input is not valid according to the validator methods
     * in the {@code Bike} class
     */
    public Bike(String iD, String type, String colour, String startTime) {
        this.validateID(iD);
        this.validateType(type);
        this.validateColour(colour);
        this.iD = iD;
        this.type = type;
        this.colour = colour;
        this.startTime = convertStartTime(startTime);
    }

    /**
     * Converts a string into a LocalDateTime object. 
     * @param startTime - as string
     * @throws IllegalArgumentException if the string is not of a pattern that matches a LocalTimeDate object. 
     * @return startTime - as LocalDateTime
     */
    private LocalDateTime convertStartTime(String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if(!startTime.matches("^\\d?\\d/\\d{2}/\\d{4}$")) {
            
            throw new IllegalArgumentException("Not a valid string to convert to a LocalTimeDate object.");
        }
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);

            return dateTime;
    }

    //Valideringsmetoder

    /**
     * Validates an input String ID for a {@code Bike} object.
     * 
     * @param iD a String to validate
     * @throws IllegalArgumentExepction if the string doesn't consist of only numerals and/or 
     * capital letters, and/or has a length not equal to 8
     */
    private void validateID(String iD) {
        this.inputNotNullValidator(iD);
        if (!Pattern.matches("[A-Z0-9]{8}", iD)) {
            throw new IllegalArgumentException("ID-format ugyldig. Må bestå av tall og store " +
            "bokstaver og ha en lengde på 8 tegn.");
        }
    }

    /**
     * Validates an input String type for a {@code Bike} object.
     * 
     * @param type a String to validate
     * @throws IllegalArgumentExepction if the collection "validTypes" in {@code Bike} does not
     * contain the input String type
     */
    private void validateType(String type) {
        this.inputNotNullValidator(type);
        if (!this.validTypes.contains(type)) {
            throw new IllegalArgumentException("Type-input ugyldig.");
        }
    }

    /**
     * Validates an input String colour for a {@code Bike} object.
     * 
     * @param colour a String to validate
     * @throws IllegalArgumentException if the collection "validColours" in {@code Bike} does not
     * contain the input String colour
     */
    private void validateColour(String colour) {
        this.inputNotNullValidator(colour);
        if (!this.validColours.contains(colour)) {
            throw new IllegalArgumentException("Farge-input ugyldig.");
        }
    }

    /**
     * Validates an Object for not being null.
     * 
     * @param input an Object to validate
     * @throws IllegalArgumentException if the input Object is null
     */
    private void inputNotNullValidator(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Input kan ikke være null");
        }
    }


    //Gettere og settere
    
    /**
     * Getter for the ID of the {@code Bike} object.
     * 
     * @return the String ID of the {@code Bike} object
     */
    public String getID() {
        return this.iD;
    }

    /**
     * Getter for the type of the {@code Bike} object.
     * 
     * @return the String type of the {@code Bike} object
     */
    public String getType() {
        return this.type;
    }

    /**
     * Getter for the colour of the {@code Bike} object.
     * 
     * @return the String colour of the {@code Bike} object
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * Getter for the startTime of the {@code Bike} object. 
     * 
     * @return the LocalDateTime startTime og the {@code Bike} object. 
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Sets the {@code startTime} of the {@code Bike} object upon the time of rental. 
     * 
     */
    public void setStartTime() {
        this.startTime = LocalDateTime.now();
    }
    
}
