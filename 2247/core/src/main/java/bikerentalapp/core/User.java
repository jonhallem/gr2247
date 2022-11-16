package bikerentalapp.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code User} class represents a user of the BikeRentalApp application.
 */
public class User {

    private String username;
    private String password;
    private Bike bike;

    /**
     * Constructs a {@code User} object with {@code username} and {@code password}.
     * Sets the users {@code bike} attribute to {@code null}, as no {@code Bike}
     * object is provided.
     *
     * @param username the {@code username} to give the {@code User} object.
     * @param password {@code password} to give the {@code User} object.
     * @throws IllegalArgumentException If {@code username} or {@code password} is
     *                                  not valid.
     */
    public User(String username, String password) {
        validateUsername(username);
        validatePassword(password);

        this.username = username;
        this.password = password;
        this.bike = null;
    }

    /**
     * Constructs a {@code User} object with {@code username},
     * {@code password} and a {@code Bike} object.
     *
     * @param username the {@code username} to give the {@code User} object.
     * @param password {@code password} to give the {@code User} object.
     * @param bike     {@code bike} object to append the {@code User} object.
     * @throws IllegalArgumentException If {@code username} or {@code password} is
     *                                  not valid.
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
     *
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the user {@code password}.
     *
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Changes the password of the {@code user}.
     *
     * @param newPassword the new password to give the {@code User} object.
     * @throws IllegalArgumentException If {@code newPassword} does not have a valid
     *                                  format in
     *                                  accordance with the {@code validatePassword}
     *                                  method.
     */
    public void changePassword(String newPassword) throws IllegalArgumentException {
        this.validatePassword(newPassword);
        this.password = newPassword;
    }

    /**
     * Returns the {@code Bike} object held by the user.
     *
     * @return Bike
     */
    public Bike getBike() {
        return this.bike;
    }

    /**
     * Sets the {@code bike} object of a user, as long as there is no {@code bike}
     * registered.
     *
     * @param bike A new {@code Bike} object.
     * @throws IllegalStateException If a bike is already registered to the user,
     *                               hence {@code bike} is not {@code null}.
     */
    public void setBike(Bike bike) {
        if (this.bike != null) {
            throw new IllegalStateException("Det er allerede registrert en sykkel");
        }
        this.bike = bike;
    }

    /**
     * Removes the {@code Bike} object held by the user and returns it, as long as
     * there is a {@code bike} registered.
     *
     * @return Bike
     * @throws IllegalStateException If there is no bike registered, hence
     *                               {@code bike} is {@code null}.
     */
    public Bike removeAndReturnBike() {
        if (this.bike == null) {
            throw new IllegalStateException(
                    "Du kan ikke fjerne og returnere en sykkel om du ikke har en");
        }
        Bike tmpBike = this.bike;
        this.bike = null;
        return tmpBike;
    }

    /**
     * Validates the user {@code username} upon initiation of a new {@code User}
     * object.
     * Checks if {@code username} is not {@code null}, contains only characters and
     * integers, and is at least of length three.
     *
     * @param username to check for validation.
     * @throws IllegalArgumentException If {@code username} is not valid.
     */
    private void validateUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Brukernavnet må være noe annet enn null.");
        }
        // bokstaver og tall, og må minst være 3 tegn langt
        String regex = "^[a-zA-Z0-9]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Ikke gyldig brukernavn");
        }
    }

    /**
     * Validates the user {@code password} upon initiation of a new {@code User}
     * object.
     * Checks if {@code password} is not {@code null}, contains at least one
     * character and one integer, and is at least of length three.
     *
     * @param password to check for validation.
     * @throws IllegalArgumentException If {@code password} does not have a valid
     *                                  format (must contain at least one letter
     *                                  and one integer, and must at least be of
     *                                  length 3).
     */
    private void validatePassword(String password) {
        // Strengere passordvalidering?
        if (password == null) {
            throw new IllegalArgumentException("Passordet må være noe annet enn null.");
        }
        // minst en bokstav og et tall, og må minst være 3 tegn langt
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        // Konkatinasjon fordi checkstyles ikke var fornøyd med en lang streng.
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Passordet oppfyller ikke kravet."
                    + "\nPassordet må inneholde minst en bokstav og ett tall,"
                    + "\nog være minst 3 tegn langt.");
        }
    }

}
