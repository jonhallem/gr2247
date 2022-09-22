package bikeRentalApp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlaceContainer implements Iterable<Place> {
    
    private List<Place> places = new ArrayList<>();

    public PlaceContainer(List<Place> placeList) {
        this.places = placeList;
    }

    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }

    @Override
    public Iterator<Place> iterator() {
        return this.places.iterator();
    }

}
