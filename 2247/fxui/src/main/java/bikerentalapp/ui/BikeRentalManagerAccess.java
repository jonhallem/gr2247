package bikerentalapp.ui;

import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
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
     * @throws IOException if an error occurs during read/write to persistence.
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
     * @throws IOException              if an error occurs during read/write to
     *                                  persistence.
     * @throws IllegalArgumentException if the username or password is already found
     *                                  in the database
     */
    public User logIn(String username, String password) throws IOException,
            IllegalArgumentException;

    /**
     * Sets a new password for the given user.
     *
     * @param username the username of the user to change the password for.
     * @param password the new password for the username.
     * @return the {@code User} object with the new password.
     * @throws IllegalArgumentException if User.java's password validation does not
     *                                  approve the password
     * @throws IOException              if an error occurs during read/write to
     *                                  persistence.
     */
    public User setUserPassword(String username, String password) throws IllegalArgumentException,
            IOException;

    /**
     * Updates the model in that the given bike is removed from the given place, and
     * added to the given user.
     *
     * @param placeName the place to remove the bike from.
     * @param bikeId    the bike to remove/add.
     * @param username  the username of the user to add the bike to.
     * @return the new {@code User} object after the bike is added.
     * @throws IOException              if an error occurs during read/write to
     *                                  persistence.
     * @throws IllegalArgumentException if the given place is not found in
     *                                  persistence or, the given bike is not found
     *                                  in the place
     */
    public User rentBike(String placeName, String bikeId, String username) throws IOException,
            IllegalArgumentException;

    /**
     * Updates the model in that the given bike is removed from the given user, and
     * added to the given place.
     *
     * @param username  the username of the user to remove the bike from.
     * @param placeName the name of the place to add the bike to.
     * @return the new user object after the bike is delivered.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    public User deliverBike(String username, String placeName) throws IOException;

    /**
     * Gets all the places available in the app, along with the bikes present there.
     *
     * @return a list of the app's {@code Place} objects.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    public PlaceContainer getPlaceContainer() throws IOException;
}
