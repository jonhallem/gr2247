package bikerentalapp.ui;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.User;
import java.io.IOException;
import java.util.List;

/**
 * Interface that defines methods to implement the Bike Rental App UI's access
 * to the model.
 */
public interface BikeRentalManagerAccess {

    /**
     * Creates a new user in the model.
     *
     * @param username the username for the new user.
     * @param password the password for the new user.
     * @return the user object created.
     */
    public User signUp(String username, String password) throws IOException;

    /**
     * Checks if the username and the password is correct, and if so returns the
     * corresponding {@code User} object.
     *
     * @param username the username for the user.
     * @param password the password for the user.
     * @return the user object with matching username and password, if it exists in
     *         the model.
     */
    public User logIn(String username, String password) throws IOException,
            IllegalArgumentException;

    /**
     * Sets a new password for the given user.
     *
     * @param user     the user to change the password for.
     * @param password the new password for the user.
     * @return the {@code User} object with the new password.
     */
    public User setUserPassword(User user, String password) throws IllegalArgumentException,
            IOException;

    /**
     * Updates the model in that the given bike is removed from the given place, and
     * added to the given user.
     *
     * @param place the place to remove the bike from.
     * @param bike  the bike to remove/add.
     * @param user  the user to add the bike to.
     * @return the new user object after the bike is added.
     */
    public User rentBike(Place place, Bike bike, User user) throws IOException,
            IllegalArgumentException;

    /**
     * Updates the model in that the given bike is removed from the given user, and
     * added to the given place.
     *
     * @param user      the user to remove the bike from.
     * @param placeName the name of the place to add the bike to.
     * @return the new user object after the bike is delivered.
     */
    public User deliverBike(User user, String placeName) throws IOException;

    /**
     * Gets all the places available in the app, along with the bikes present there.
     *
     * @return a list of the app's place objects.
     */
    public List<Place> getPlaces() throws IOException;
}
