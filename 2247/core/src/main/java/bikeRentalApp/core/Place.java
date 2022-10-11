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
    
    /**
     * Constucts a {@code Place} object used to store {@code Bike} objects at a specific location.
     * 
     * @param name the String name of the {@code Place} object
     * @param maximumNumberOfBikes the maximum number of bikes allowed in the {@code Place} object
     * @throws IllegalArguemtException if name or maximumNumberOfBikes are not valid according to 
     * the correspinding validators in the {@code Place} class
     */
    public Place(String name, int maximumNumberOfBikes){
        this.nameValidator(name);
        this.maximumNumberOfBikesValidator(maximumNumberOfBikes);
        this.name = name;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.bikes = new ArrayList<>();
    }

    /**
     * Constucts a {@code Place} object used to store {@code Bike} objects at a specific location.
     * 
     * @param name the String name of the {@code Place} object
     * @param maximumNumberOfBikes the maximum number of bikes allowed in the {@code Place} object
     * @param bikes a list 
     * @throws IllegalArguemtException if name or maximumNumberOfBikes are not valid according to 
     * the correspinding validators in the {@code Place} class, or if bikes is null
     * @throws IllegalStateException if the number of {@code Bike} objects in input bikes is above
     * the limit given in input maximumNumberOfBikes
     */
    public Place(String name, int maximumNumberOfBikes, List<Bike> bikes){
        this.nameValidator(name);
        this.maximumNumberOfBikesValidator(maximumNumberOfBikes);
        this.inputNotNullValidator(bikes);
        if (bikes.size() > maximumNumberOfBikes) {
            throw new IllegalStateException("Stedet kan ikke inneholde alle syklene angitt, gitt" +
            " begrensingen på antall sykler som ble angitt.");
        }
        this.name = name;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.bikes = new ArrayList<>(bikes);
    }



    //Gettere

    /**
     * Getter for the name of the {@code Place} object.
     * 
     * @return the String name of the {@code Place} object
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the int maximumNumberOfBikes of the {@code Place} object.
     * 
     * @return the int maximumNumberOfBikes of the {@code Place} object
     */
    public int getMaximumNumberOfBikes() {
        return this.maximumNumberOfBikes;
    }

    /**
     * Getter for a copy of bike-list of the {@code Place} object, which contains all the {@code Bike}
     * objects in the {@code Place} object.
     * 
     * @return a copy of the list "bikes" in the {@code Place} object
     */
    public List<Bike> getBikes() {
        return new ArrayList<>(bikes);
    }


    //Legge til og fjerne sykler
    
    /**
     * Removes the {@code Bike} object with the given bikeID from the {@code Place} object, and 
     * returns it.
     * 
     * @param bikeID the id String of the {@code Bike} object to remove
     * @throws IllegalArgumentException if the bikeID input is not a valid bikeID according to 
     * the validateID method in the {@code Place} class
     * @return the {@code Bike} object that has been removed from the {@code Place} object
     */
    public Bike removeAndGetBike(String bikeID) {
        validateID(bikeID);
        Bike bikeToRemove = this.bikes.stream().filter(bike -> bike.getID().equals(bikeID)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Det finnes ingen sykkel her med denne ID-en."));
        this.bikes.remove(bikeToRemove);
        return bikeToRemove;
    }

    /**
     * Adds the given {@code Bike} object to the {@code Place} object's list of bikes
     * 
     * @param bike the {@code Bike} object to add 
     * @throws IllegalStateException if the size of the {@code Place} object's list of bikes is equal
     * to the object's maximumNumberOfBikes before adding the given {@code Bike} object
     * @throws IllegalArgumentException if the given {@code Bike} object is null
     */
    public void addBike(Bike bike) {
        if (this.bikes.size() == this.maximumNumberOfBikes) {
            throw new IllegalStateException("Stedet er fullt, sykkelen må plasseres på et annet sted.");
        }
        inputNotNullValidator(bike);
        this.bikes.add(bike);
    }


    //Valideringsmetoder

    /**
     * Validates an input String name for a {@code Place} object.
     * 
     * @param name a String to validate
     * @throws IllegalArgumentException if the name is null or a blank String
     */
    private void nameValidator(String name) {
        this.inputNotNullValidator(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("Navnet må inneholde minst ett tegn.");
        }
    }

    /**
     * Validates an input int maximumNumberOfBikes for a {@code Place} object.
     * 
     * @param number an int to validate
     * @throws IllegalArgumentException if the number is smaller than one
     */
    private void maximumNumberOfBikesValidator(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Stedet må minst kunne holde på én sykkel.");
        }
    }

    /**
     * Validates an input String iD for a {@code Bike} object.
     * 
     * @param iD a String to validate
     * @throws IllegalArgumentException if the iD is null or the iD String doesn't consist 
     * of only numerals and/or capital letters, and/or has a length not equal to 8
     */
    private void validateID(String iD) {
        this.inputNotNullValidator(iD);
        if (!Pattern.matches("[A-Z0-9]{8}", iD)) {
            throw new IllegalArgumentException("ID-format ugyldig. Må bestå av tall og store bokstaver og ha en lengde på 8 tegn.");
        }
    }

    /**
     * Validates an Object for not being null.
     * 
     * @param input an Object to validate
     * @throws IllegalArgumentException if the input Object is null
     */
    private void inputNotNullValidator(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Input kan ikke være null");
        }
    }


    //Iteratormetode

    /**
     * Returns an iterator for the {@code Place} object
     * 
     * @return an iterator that iterates over the {@code Bike}
     * objects contained within the {@code Place} object
     */
    @Override
    public Iterator<Bike> iterator() {
        return this.bikes.iterator();
    }

}
