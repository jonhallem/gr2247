package bikeRentalApp.core;

import java.util.ArrayList;
import java.util.List;

public class BikeRentalManager {

    // ------------ Tilstand ---------------

    private List<Place> places;

    private List<User> users;

    private User loggedInUser;


    // ----------- Konstruktør -------------

    
    public BikeRentalManager() {

        // TODO: kan vurdere å fylle inn tilstander
       this.places =  new ArrayList<>();
       this.users =  new ArrayList<>();
       this.loggedInUser = null;
    }


    // -------------- Gettere og settere ---------------


    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }




    // ----------- Metoder -------------

    public void logIn(String username, String password) {

        for (User user : users) {
           if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.loggedInUser = user;
                return;
           }
        }
        throw new IllegalArgumentException("Brukernavn eller passord er ikke gyldig");

    }

    public void signUp(String username, String password) {

        for (User user : users) {
           if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Brukernavnet er tatt!");
           }
        }
        User user = new User(username, password);
        this.users.add(user);
        this.loggedInUser = user;

    }

    public void rentBike(String placeName, String bikeID) {

        for (Place place : places) {
            if (place.getName().equals(placeName)) {
                for (Bike bike : place) {
                    if (bike.getID().equals(bikeID)) {
                        this.loggedInUser.setBike(bike);
                        place.removeBike(bikeID);
                        return;
                    }
                }
            }
        }
    }

    public void deliverBike(String placeName) {

        for (Place place : places) {
            if (place.getName().equals(placeName)) {
                place.addBike(loggedInUser.removeAndReturnBike());
            }
        }
    }






}
