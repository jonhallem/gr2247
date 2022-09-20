package bikeRentalApp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Place implements Iterable<Bike> {
    
    //Tilstand
    private String name;
    private int maximumNumberOfBikes;
    private List<Bike> bikes;

    //Konstruktører
    public Place(String name, int maximumNumberOfBikes){
        this.nameValidator(name);
        if (maximumNumberOfBikes < 1) {
            throw new IllegalArgumentException("Stedet må minst kunne holde på én sykkel.");
        }
        this.name = name;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.bikes = new ArrayList<>();
    }

    public Place(String name, int maximumNumberOfBikes, List<Bike> bikes){
        this.nameValidator(name);
        if (maximumNumberOfBikes < 1) {
            throw new IllegalArgumentException("Stedet må minst kunne holde på én sykkel.");
        }
        this.name = name;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.bikes = bikes;
    }



    //Gettere

    public String getName() {
        return this.name;
    }

    public int getMaximumNumberOfBikes() {
        return this.maximumNumberOfBikes;
    }

    public List<Bike> getBikes() {
        return new ArrayList<>(bikes);
    }


    //Legge til og fjerne sykler
    
    public Bike removeAndGetBike(String bikeID) {
        validateID(bikeID);
        Bike bikeToRemove = this.bikes.stream().filter(bike -> bike.getID().equals(bikeID)).findFirst().get();
        this.bikes.remove(bikeToRemove);
        return bikeToRemove;
    }

    public void addBike(Bike bike) {
        if (this.bikes.size() == this.maximumNumberOfBikes) {
            throw new IllegalStateException("Stedet er fullt, sykkelen må plasseres på et annet sted.");
        }
        inputNotNullValidator(bike);
        this.bikes.add(bike);
    }


    //Valideringsmetoder

    private void nameValidator(String name) {
        this.inputNotNullValidator(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("Navnet må inneholde minst ett tegn.");
        }
    }

    private void validateID(String iD) {
        this.inputNotNullValidator(iD);
        if (!Pattern.matches("[A-Z0-9]{8}", iD)) {
            throw new IllegalArgumentException("ID-format ugyldig. Må bestå av tall og store bokstaver og ha en lengde på 8 tegn.");
        }
    }

    private void inputNotNullValidator(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Input kan ikke være null");
        }
    }


    //Iteratormetode

    @Override
    public Iterator<Bike> iterator() {
        return this.bikes.iterator();
    }

}
