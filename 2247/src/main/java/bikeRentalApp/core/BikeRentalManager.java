package bikeRentalApp.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BikeRentalManager {

    // ------------ Tilstand ---------------

    private List<Place> places;

    private List<User> users;

    private User loggedInUser;

    private BikeRentalDataHandler dataHandler;


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

    public List<Bike> getBikeInPlace(Place place) {
        return new ArrayList<>(place.getBikes());
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }



    // ----------- Metoder -------------

    public void testMethod() {
        Bike bike1 = new Bike("12345678", "Fjellsykkel", "Blå");
        Bike bike2 = new Bike("87654321", "Tandemsykkel", "Rød");

        Place place1 = new Place("Munkholmen", 2);
        Place place2 = new Place("Nidaros", 5);

        place1.addBike(bike1);
        place1.addBike(bike2);
        this.places.add(place1);
        this.places.add(place2);
        
    }


    public void updateUserList() {

        dataHandler = new BikeRentalDataHandler();

        try {
            this.users = dataHandler.loadUsers("BikeRentalUserData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlaceList() {
        dataHandler = new BikeRentalDataHandler();
        try {
            this.places = dataHandler.loadPlace("BikeRentalPlaceData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBikeList() {
        dataHandler = new BikeRentalDataHandler();
        try {
            dataHandler.loadBike("BikeRentalBikeData");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logIn(String username, String password) {

        updateUserList();

        for (User user : users) {
           if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.loggedInUser = user;
                return;
           }
        }
        throw new IllegalArgumentException("Brukernavn eller passord er ikke gyldig");

    }

    public void signUp(String username, String password) {

        updateUserList();

        for (User user : users) {
           if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Brukernavnet er tatt!");
           }
        }
        User user = new User(username, password);
        this.users.add(user);
        try {
            dataHandler.saveUser("BikeRentalUserData", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.loggedInUser = user;

    }

    public void rentBike(String placeName, String bikeID) {

        for (Place place : places) {
            if (place.getName().equals(placeName)) {
                for (Bike bike : place) {
                    if (bike.getID().equals(bikeID)) {
                        this.loggedInUser.setBike(place.removeAndGetBike(bikeID));
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
