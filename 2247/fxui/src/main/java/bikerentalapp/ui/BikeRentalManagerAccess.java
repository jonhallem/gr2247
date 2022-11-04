package bikerentalapp.ui;

import java.io.IOException;
import java.util.List;

import bikerentalapp.core.User;
import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;

public interface BikeRentalManagerAccess {

    /**
     * Creates a new user in the model
     *
     * @param username the username for the new user
     * @param password the password for the new user
     * @return the user object created
     */
    public User signUp(String username, String password) throws IOException;

    /**
     * Checks if the username and the password is correct
     * 
     * 
     * @param username the username for the user
     * @param password the password for the user
     * @return the user object with matching username and password, if it exists in
     *         the model
     */
    public User logIn(String username, String password) throws IOException;

    /**
     * Sets a new password for the given user
     * 
     * @param user     the user to change the password for
     * @param password the new password for the user
     */
    public void setUserPassword(User user, String password) throws IllegalArgumentException, IOException;

    /**
     * Updates the model in that the given bike is removed from the given place, and
     * added to the given user
     * 
     * @param place the place to remove the bike from
     * @param bike  the bike to remove/add
     * @param user  the user to add the bike to
     */
    public void rentBike(Place place, Bike bike, User user) throws IOException, IllegalArgumentException;

    /**
     * Updates the model in that the given bike is removed from the given user, and
     * added to the given bike
     * 
     * @param user  the user to remove the bike from
     * @param place the place to add the bike to
     * @param bike  the bike to remove/add
     */
    public void deliverBike(User user, Place place, Bike bike) throws IOException;

    /**
     * Gets all the places available in the app, along with the bikes present there
     * 
     * @return a list of the app's place objects
     */
    public List<Place> getPlaces() throws IOException;
}
