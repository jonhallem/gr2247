package bikeRentalApp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code PlaceContainer} is a iterable class, holding a series of {@code Place} objects. 
 */

public class PlaceContainer implements Iterable<Place> {
    
    private List<Place> places = new ArrayList<>();

    /**
     * Constructs a {@code PlaceContainer} object with a list of {@code Place} objects. 
     * @param placeList
     */
    public PlaceContainer(List<Place> placeList) {
        this.places = placeList;
    }

    /**
     * Returns a new list of the registered {@code Place} objects.
     * @return List
     */
    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }

    @Override
    public Iterator<Place> iterator() {
        return this.places.iterator();
    }

}
