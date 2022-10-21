package bikeRentalApp.core;

import java.io.IOException;
import java.util.List;

import bikeRentalApp.json.BikeRentalPersistence;

public class BikeRentalManager {

    // ------------ Tilstand ---------------

    private User loggedInUser;

    private BikeRentalPersistence bikeRentalPersistence;


    // ----------- Konstruktør -------------

    /**
     * Constructs a BikeRentalManager object with loggedInUser and bikeRentalPersistence
     */
    public BikeRentalManager() {

        // TODO: kan vurdere å fylle inn tilstander

       this.loggedInUser = null;
       this.bikeRentalPersistence = new BikeRentalPersistence();
    }


    // -------------- Gettere og settere ---------------


    /**
     * Returns a list of places
     * @return List of placeobjects
     * @throws IOException if an error occurs during filehandling
     */
    public List<Place> getPlaces() throws IOException {
        PlaceContainer placeContainer = this.bikeRentalPersistence.readPlaceContainer();
        return placeContainer.getPlaces();
    }

        /**
     * Returns an user
     * @return user object
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Returns the users registered bike
     * @return Bike object. Null if user has no bike
     */
    public Bike getUserBike() {
        return loggedInUser.getBike();
    }



    // ----------- Metoder -------------


    /**
     * Sets the loggedInUser to the correct user in the database
     * @param username The string username
     * @param password The string password
     * @throws IllegalArgumentException if the username or password is not found in the database
     */
    public void logIn(String username, String password) throws IOException {

        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (User user : userContainer.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.loggedInUser = user;
                return;
           }
        }
        throw new IllegalArgumentException("Brukernavn eller passord er ikke gyldig");
    }

    /**
     * Creates a user and appends it to the database. The created user is also set to loggedInUser
     * @param username A unique string username not found in the database
     * @param password A strong string password
     * @throws IllegalArgumentException if the username or password is already found in the database
     */
    public void signUp(String username, String password) throws IOException {


        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (User user : userContainer.getUsers()) {
           if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Brukernavnet er tatt!");
           }
        }
        User user = new User(username, password);
        userContainer.addUser(user);
        this.loggedInUser = user;
        
        bikeRentalPersistence.writeUserContainer(userContainer);
    }


 
    /**
     * Selected bike is removed from its place and appended to the user. The database is update with updated information about
     * the place, bike and user - starting the bikerenting.
     * @param placeName the string place selected to rent bike from
     * @param bikeID the string bike selected to rent
     * @throws IOException if filehandling has an unexpected error
     */
    public void rentBike(String placeName, String bikeID) throws IOException{

        PlaceContainer placeContainer = bikeRentalPersistence.readPlaceContainer();
        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (Place place : placeContainer.getPlaces()) {
            if (place.getName().equals(placeName)) {
                for (Bike bike : place) {
                    if (bike.getID().equals(bikeID)) {
                        Bike bikeToRent = place.removeAndGetBike(bikeID);
                        bikeToRent.setStartTime();
                        this.loggedInUser.setBike(bikeToRent);
                        userContainer.findUser(loggedInUser.getUsername()).setBike(bikeToRent);     //sykkel må også legges til i userContainer, så tilstanden lagres
                        bikeRentalPersistence.writePlaceContainer(placeContainer);
                        bikeRentalPersistence.writeUserContainer(userContainer);
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Stedet eksisterer ikke");
    }

    /**
     * The bike appended to the user is removed and appended to the selected place - ending the bikerenting.
     * @param placeName the string place selected to deliver bike to
     * @throws IOException if filehandling has an unexpected error
     */
    public void deliverBike(String placeName) throws IOException {

        PlaceContainer placeContainer = bikeRentalPersistence.readPlaceContainer();
        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (Place place : placeContainer.getPlaces()) {
            if (place.getName().equals(placeName)) {
                Bike bikeToDeliver = loggedInUser.removeAndReturnBike();
                userContainer.findUser(loggedInUser.getUsername()).removeAndReturnBike();       //Sykkel må også fjernes fra bruker i userContainer
                place.addBike(bikeToDeliver);
            }
        }

        bikeRentalPersistence.writePlaceContainer(placeContainer);
        bikeRentalPersistence.writeUserContainer(userContainer);
    }

}
