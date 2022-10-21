package bikeRentalApp.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code User} class represents a user of the BikeRentalApp application. 
 */
public class User {
    
    private String username;
    private String password;
    private Bike bike;
    private List<List<String>> rentalHistory;
    // "bikeID, timeStart, timeEnd, totalRentalTime"

    /**
     * Constructs a {@code User} object with {@code username} and {@code password}. 
     * <p>
     * Sets the users {@code bike} attribute to {@code null}, as no {@code Bike} object is provided.
     * 
     * @param username
     * @param password
     * @throws IllegalArgumentException If {@code username} or {@code password} is not valid.
     */
    public User (String username, String password) {
        validateUsername(username);
        validatePassword(password);
        
        this.username = username;
        this.password = password;
        this.bike = null;
        this.rentalHistory = new ArrayList<>();
    }

    /**
     * Constructs a {@code User} object with {@code username}, {@code password} and a {@code Bike} object. 
     * 
     * @param username
     * @param password
     * @param bike
     * @throws IllegalArgumentException If {@code username} or {@code password} is not valid.
     */
    public User(String username, String password, Bike bike) {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
        setBike(bike);
    }
    
    /**
     * Returns the user {@code username}. 
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the user {@code password}. 
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the {@code Bike} object held by the user. 
     * @return Bike
     */
    public Bike getBike() {
        return this.bike;
    }
    
    /**
     * Returns the rental history of the user. 
     * @return List
     */
    public List<List<String>> getRentalHistory() {
        return new ArrayList<>(this.rentalHistory);
    }

    /**
     * Sets the {@code bike} object of a user, as long as there is no {@code bike} registered.  
     *  
     * @param bike A new {@code Bike} object.
     * @throws IllegalStateException If a bike is already registered to the user, hence {@code bike} is not {@code null}.
     */
    public void setBike(Bike bike) {
        if(this.bike != null) {
            throw new IllegalStateException("Det er allerede registrert en sykkel");
        }
        this.bike = bike;
    }

    /**
     * Removes the {@code Bike} object held by the user and returns it, as long as there is a {@code bike} registered.
     * @return Bike
     * @throws IllegalStateException If there is no bike registered, hence {@code bike} is {@code null}.
     */
    public Bike removeAndReturnBike() {
        if(this.bike == null) {
            throw new IllegalStateException("Du kan ikke fjerne og returnere en sykkel om du ikke har en");
        }
        Bike tmpBike = this.bike;
        this.bike = null;
        return tmpBike;
    }

    private List<List<String>> addToRentalHistory(Bike bike) {
        List<String> tmpList = new ArrayList<>();
        LocalDateTime endTime = LocalDateTime.now();
        tmpList.add(bike.getID());
        tmpList.add(bike.getStartTime());
        tmpList.add(endTime.toString());
        tmpList.add()


        if (startTime == null) {

        } else {
            endTime = endTime;
        }

        rentalHistory = getRentalHistory();
    }

    /**
     * Validates the user {@code username} upon initiation of a new {@code User} object. 
     * <p>
     * Checks if {@code username} is not {@code null}, contains only characters and integers, and is at least of length three.
     * 
     * @param username
     * @throws IllegalArgumentException If {@code username} is not valid.
     */
    private void validateUsername(String username) {
        if(username == null) {
            throw new IllegalArgumentException("Brukernavnet må være noe annet enn null.");
        }
        String regex = "^[a-zA-Z0-9]{3,}$"; // bokstaver og tall, og må minst være 3 tegn langt
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Ikke gyldig brukernavn");
        } 
    }

     /**
     * Validates the user {@code password} upon initiation of a new {@code User} object. 
     * <p>
     * Checks if {@code password} is not {@code null}, contains at least one character and one integer, and is at least of length three. 
     * 
     * @param password
     * @throws IllegalArgumentException If {@code password} is not valid.
     */
    private void validatePassword(String password) {
        // Strengere passordvalidering?
        if(password == null) {
            throw new IllegalArgumentException("Passordet må være noe annet enn null.");
        }
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{3,}$"; //  minst en bokstav og et tall, og må minst være 3 tegn langt
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) {
            throw new IllegalArgumentException("Passordet oppfyller ikke kravet. \nPassordet må inneholde minst en bokstav og ett tall, \nog være minst 3 tegn langt.");
        } 
    }

}
