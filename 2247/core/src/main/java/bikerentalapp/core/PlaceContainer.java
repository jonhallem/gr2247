package bikerentalapp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code PlaceContainer} is a iterable class, holding a series of
 * {@code Place} objects.
 */

public class PlaceContainer implements Iterable<Place> {

    private List<Place> places = new ArrayList<>();

    /**
     * Constructs a {@code PlaceContainer} object with a list of {@code Place}
     * objects.
     *
     * @param placeList to make into a placecontainer.
     */
    public PlaceContainer(List<Place> placeList) {
        this.places = new ArrayList<>(placeList);
    }

    /**
     * Returns a new list of the registered {@code Place} objects.
     *
     * @return List
     */
    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }

    /**
     * Adds a new place to the registered {@code Place} object.
     * Used during testing
     */
    public void addPlace(String placeName, int numberOfBikes) {
        places.add(new Place(placeName, numberOfBikes));
    }

    /**
     * Removes a place from the registered {@code Place} object.
     * Used during testing
     */
    public void removePlace(String placeName) {

        for (Place place : getPlaces()) {
            if (place.getName().equals(placeName)) {
                places.remove(place);
            }
        }
    }

    /**
     * Finds a {@code Place} object with the given {@code placeName}
     * in the list of places, {@code Places}, and returns it.
     *
     * @param placeName to find in the list of {@code Places}.
     * @return Place
     * @throws IllegalArgumentException If there is no {@code Place} with the given
     *                                  {@code placeName}.
     */
    public Place findPlace(String placeName) {
        return this.places.stream().filter(place -> place.getName().equals(placeName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Et sted med dette navnet finnes ikke"));
    }

    @Override
    public Iterator<Place> iterator() {
        return this.places.iterator();
    }

}
