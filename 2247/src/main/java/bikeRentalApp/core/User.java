package bikeRentalApp.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    
    private String username;
    private String password;
    private Bike bike;

    public User (String username, String password) {
        validateUsername(username);
        validatePassword(password);
        
        this.username = username;
        this.password = password;
        this.bike = null;
    }
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Bike getBike() {
        return this.bike;
    }

    public void setBike(Bike bike) {
        if(this.bike != null) {
            throw new IllegalStateException("Det er allerede registrert en sykkel");
        }
        this.bike = bike;
    }

    public Bike removeAndReturnBike() {
        if(this.bike == null) {
            throw new IllegalStateException("Du kan ikke fjerne og returnere en sykkel om du ikke har en");
        }
        Bike tmpBike = this.bike;
        this.bike = null;
        return tmpBike;
    }

    private void validateUsername(String username) {

        // Forbehold om at BikeRentalManager sjekker om brukernavnet er tilgjengelig
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

    @Override
    public String toString() {
        return "username: " + getUsername() + ", password: " + getPassword() + ", bike: " + getBike().getID();
    }
}
