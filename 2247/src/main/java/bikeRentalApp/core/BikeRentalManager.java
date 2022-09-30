package bikeRentalApp.core;

import java.io.IOException;
import java.util.List;

import bikeRentalApp.json.BikeRentalPersistence;

public class BikeRentalManager {

    // ------------ Tilstand ---------------

    private User loggedInUser;

    private BikeRentalPersistence bikeRentalPersistence;


    // ----------- Konstruktør -------------

    
    public BikeRentalManager() {

        // TODO: kan vurdere å fylle inn tilstander

       this.loggedInUser = null;
       this.bikeRentalPersistence = new BikeRentalPersistence();
    }


    // -------------- Gettere og settere ---------------


    public List<Place> getPlaces() throws IOException {
        PlaceContainer placeContainer = this.bikeRentalPersistence.readPlaceContainer();
        return placeContainer.getPlaces();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Bike getUserBike() {
        return loggedInUser.getBike();
    }



    // ----------- Metoder -------------



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


    public void rentBike(String placeName, String bikeID) throws IOException, IllegalArgumentException {

        PlaceContainer placeContainer = bikeRentalPersistence.readPlaceContainer();
        UserContainer userContainer = bikeRentalPersistence.readUserContainer();

        for (Place place : placeContainer.getPlaces()) {
            if (place.getName().equals(placeName)) {
                for (Bike bike : place) {
                    if (bike.getID().equals(bikeID)) {
                        Bike bikeToRent = place.removeAndGetBike(bikeID);
                        this.loggedInUser.setBike(bikeToRent);
                        userContainer.findUser(loggedInUser.getUsername()).setBike(bikeToRent);     //sykkel må også legges til i userContainer, så tilstanden lagres
                        break;
                    }
                }
            }
        }

        bikeRentalPersistence.writePlaceContainer(placeContainer);
        bikeRentalPersistence.writeUserContainer(userContainer);
    }


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
