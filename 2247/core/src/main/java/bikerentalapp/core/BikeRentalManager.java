package bikerentalapp.core;

import bikerentalapp.json.BikeRentalPersistence;
import java.io.IOException;
import java.util.List;

public class BikeRentalManager {

    // ------------ Tilstand ---------------

    private BikeRentalPersistence bikeRentalPersistence;

    // ----------- Konstruktør -------------

    /**
     * Constructs a BikeRentalManager object with loggedInUser and
     * bikeRentalPersistence
     */
    public BikeRentalManager() {

        this.bikeRentalPersistence = new BikeRentalPersistence();
    }

    // -------------- Gettere og settere ---------------

    /**
     * 
     * Returns the BikeRentalPersistence held by the BikeRentalManager.
     * 
     * @return BikeRentalPersistence
     */
    public BikeRentalPersistence getBikeRentalPersistence() {
        return this.bikeRentalPersistence;
    }

    /**
     * Returns a list of places
     * 
     * @return List of placeobjects
     * @throws IOException if an error occurs during filehandling
     */
    public List<Place> getPlaces() throws IOException {
        PlaceContainer placeContainer = this.bikeRentalPersistence.readPlaceContainer();
        return placeContainer.getPlaces();
    }

    /**
     * Returns a placeContainer, containing a list of places.
     * 
     * @return {@code PlaceContainer}
     * @throws IOException if an error occurs during filehandling
     */
    public PlaceContainer getPlaceContainer() throws IOException {
        PlaceContainer placeContainer = this.bikeRentalPersistence.readPlaceContainer();
        return placeContainer;
    }

    // ----------- Metoder -------------

    /**
     * Changes the password of the logged in user and saves the change to
     * persistence
     * 
     * @param username    the username of the user
     * @param newPassword the new password of the user
     * @return the {@code User} object with a changed password
     * @throws IOException              if an error occurs during filehandling
     * @throws IllegalArgumentException if User.java's password validation does not
     *                                  approve the password
     */
    public User setUserPassword(String username, String newPassword)
            throws IllegalArgumentException, IOException {
        UserContainer userContainer = this.bikeRentalPersistence.readUserContainer();
        User loggedInUserFromContainter = userContainer.findUser(username);

        loggedInUserFromContainter.changePassword(newPassword);

        this.bikeRentalPersistence.writeUserContainer(userContainer);

        return loggedInUserFromContainter;
    }

    /**
     * Sets the loggedInUser to the correct user in the database
     * 
     * @param username The string username
     * @param password The string password
     * @return the logged in {@code User} object
     * @throws IllegalArgumentException if the username or password is not found in
     *                                  the database
     */
    public User logIn(String username, String password) throws IOException, IllegalArgumentException {

        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (User user : userContainer.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Brukernavn eller passord er ikke gyldig");
    }

    /**
     * Creates a user and appends it to the database. The created user is also set
     * to loggedInUser
     * 
     * @param username A unique string username not found in the database
     * @param password A strong string password
     * @return the signed up {@code User} object
     * @throws IllegalArgumentException if the username or password is already found
     *                                  in the database
     */
    public User signUp(String username, String password) throws IOException {

        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (User user : userContainer.getUsers()) {
            if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Brukernavnet er tatt!");
            }
        }
        User user = new User(username, password);
        userContainer.addUser(user);

        bikeRentalPersistence.writeUserContainer(userContainer);

        return user;
    }

    /**
     * Updates the persistence and user: removes the {@code Bike} object from the
     * given
     * {@code Place} object, and assigns it to the given {@code User} object.
     * 
     * @param nameOfPlaceToRentFrom the {@code Place} object selected to rent bike
     *                              from
     * @param bikeToRentId          the {@code Bike} object selected to rent
     * @param username              the {@code User} object that rents the bike
     * @throws IOException              if read/write to file has an unexpected
     *                                  error
     * @throws IllegalArgumentException if the given place is not found in
     *                                  persistence or, the given bike is not found
     *                                  in the place
     */
    public User rentBike(String nameOfPlaceToRentFrom, String bikeToRentId, String username)
            throws IOException, IllegalArgumentException {

        PlaceContainer placeContainer = bikeRentalPersistence.readPlaceContainer();
        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        Place placeToRentFromInPlaceContainer = placeContainer.findPlace(nameOfPlaceToRentFrom);
        if (placeToRentFromInPlaceContainer.getBikes().stream().filter(bike -> bike.getID().equals(bikeToRentId))
                .count() == 0) {
            throw new IllegalArgumentException("Det gitte stedet inneholder ikke den gitte sykkelen.");
        }
        User userToRentBike = userContainer.findUser(username);

        userToRentBike.setBike(placeToRentFromInPlaceContainer.removeAndGetBike(bikeToRentId));

        bikeRentalPersistence.writePlaceContainer(placeContainer);
        bikeRentalPersistence.writeUserContainer(userContainer);

        return userToRentBike;
    }

    /**
     * The bike appended to the user is removed and appended to the selected place -
     * ending the bikerenting.
     * 
     * @param user                   the user to find in persistence to remove the
     *                               bike from
     * @param nameOfPlaceToDeliverTo the string place selected to deliver bike to
     * @return the new user object after the bike is delivered
     * @throws IOException if filehandling has an unexpected error
     */
    public User deliverBike(String username, String nameOfPlaceToDeliverTo) throws IOException {

        PlaceContainer placeContainer = bikeRentalPersistence.readPlaceContainer();
        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        User userToDeliverBike = userContainer.findUser(username);

        Bike bikeToDeliver = userToDeliverBike.removeAndReturnBike();
        placeContainer.findPlace(nameOfPlaceToDeliverTo).addBike(bikeToDeliver);

        bikeRentalPersistence.writePlaceContainer(placeContainer);
        bikeRentalPersistence.writeUserContainer(userContainer);

        return userToDeliverBike;
    }

}