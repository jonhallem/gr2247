package bikerentalapp.ui;

import bikerentalapp.core.BikeRentalManager;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import java.io.IOException;

/**
 * Class that implements the interface BikeRentalManagerAccess. Makes the model
 * accessable to the UI directly.
 */
public class DirectBikeRentalManagerAccess implements BikeRentalManagerAccess {

    private BikeRentalManager bikeRentalManager;

    /**
     * Constructor which instantiates a {@code DirectBikeRentalManagerAccess} object
     * with a new {@code BikeRentalManager} object.
     */
    public DirectBikeRentalManagerAccess() {
        this.bikeRentalManager = new BikeRentalManager();
    }

    /**
     * Creates a new user, adds it to the models's persistence, and returns it.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return the {@code User} object created.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    @Override
    public User signUp(String username, String password) throws IOException {
        return this.bikeRentalManager.signUp(username, password);
    }

    /**
     * Checks if the username and the password is correct, and if so returns the
     * corresponding {@code User} object.
     *
     * @param username the username for the user.
     * @param password the password for the user.
     * @return the user object with matching username and password, if it exists in
     *         the model.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    @Override
    public User logIn(String username, String password) throws IOException {
        return this.bikeRentalManager.logIn(username, password);
    }

    /**
     * Sets a new password for the given user.
     *
     * @param username the user to change the password for.
     * @param password the new password for the user.
     * @return the {@code User} object with the new password.
     * @throws IOException              if an error occurs during read/write to
     *                                  persistence.
     * @throws IllegalArgumentException if User.java's password validation does not
     *                                  approve the password
     */
    @Override
    public User setUserPassword(String username, String password)
            throws IOException, IllegalArgumentException {
        return this.bikeRentalManager.setUserPassword(username, password);
    }

    /**
     * Updates the model in that the given bike is removed from the given place, and
     * added to the given user.
     *
     * @param placeName the place to remove the bike from.
     * @param bikeId    the bike to remove/add.
     * @param username  the user to add the bike to.
     * @return the new user object after the bike is added.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    @Override
    public User rentBike(String placeName, String bikeId, String username) throws IOException {
        return this.bikeRentalManager.rentBike(placeName, bikeId, username);
    }

    /**
     * Updates the model in that the given bike is removed from the given user, and
     * added to the given place.
     *
     * @param username  the user to remove the bike from.
     * @param placeName the name of the place to add the bike to.
     * @return the new user object after the bike is delivered.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    @Override
    public User deliverBike(String username, String placeName) throws IOException {
        return this.bikeRentalManager.deliverBike(username, placeName);
    }

    /**
     * Gets all the places available in the app, along with the bikes present there.
     *
     * @return a list of the app's place objects.
     * @throws IOException if an error occurs during read/write to persistence.
     */
    @Override
    public PlaceContainer getPlaceContainer() throws IOException {
        return this.bikeRentalManager.getPlaceContainer();
    }

}
