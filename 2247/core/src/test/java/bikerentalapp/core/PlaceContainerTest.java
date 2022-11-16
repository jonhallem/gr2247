package bikerentalapp.core;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaceContainerTest {

    private PlaceContainer placeContainer;
    private List<Place> testPlaceList;
    private Place place1;
    private Place place2;

    @BeforeEach
    public void setUp() throws IOException {
        System.out.println("Initialiserer, setup.");
        // List<Place> places = new ArrayList<>();
        // this.bikeRentalManager = new BikeRentalManager();
        // this.placeContainer = new PlaceContainer(bikeRentalManager.getPlaces());
        place1 = new Place("TestPlace1", 1);
        place2 = new Place("TestPlace2", 1);
        testPlaceList = new ArrayList<>(Arrays.asList(place1, place2));
        placeContainer = new PlaceContainer(testPlaceList);
    }

    @Test
    @DisplayName("Tester det å hente listen over stedene")
    void testConstructor_and_getPlaces() {

        assertEquals(placeContainer.getPlaces(), testPlaceList,
                "Listen getPlaces returner skal være den samme som ble satt inn i konstruktøren.");
    }

    @Test
    @DisplayName("Tester det å legge til et place objekt")
    void testAddPlace() {

        assertEquals(placeContainer.getPlaces().size(), 2,
                "Listen getPlaces returnerer skal være av lengde 2.");

        placeContainer.addPlace("TestPlace3", 1);
        assertEquals(placeContainer.getPlaces().size(), 3,
                "Listen getPlaces returnerer skal være av lengde 3, etter at et nytt place objekt ble lagt til. ");

        assertEquals(placeContainer.getPlaces().stream().filter(place -> place.getName().equals("TestPlace3")).count(),
                1,
                "Listen getPlaces returnerer skal nå inneholde et objekt med navnet 'TestPlace3'.");
    }

    @Test
    @DisplayName("Tester det å fjerne et place objekt")
    void testRemovePlace() {
        assertEquals(placeContainer.getPlaces().size(), 2,
                "Listen getPlaces returnerer skal være av lengde 2.");

        placeContainer.removePlace("TestPlace2");
        assertEquals(placeContainer.getPlaces().size(), 1,
                "Listen getPlaces returner skal være av lenge 1, etter at et objekt ble fjernet. ");

        assertEquals(placeContainer.getPlaces().stream().filter(place -> place.getName().equals("TestPlace2")).count(),
                0,
                "Listen getPlaces returnerer skal nå ikke inneholde et objekt med navnet 'TestPlace2'.");

    }

    @Test
    @DisplayName("Tester det å fjerne et place objekt")
    void testFindPlace() {
        assertEquals(placeContainer.findPlace("TestPlace1"), place1,
                "findPlace() skal returnere place1 når navnet til place1 brukes som argument.");

        assertEquals(placeContainer.findPlace("TestPlace2"), place2,
                "findPlace() skal returnere place2 når navnet til place2 brukes som argument.");

        assertThrows(IllegalArgumentException.class, () -> {
            placeContainer.findPlace("TestPlace3");
        },
                "Å finne et sted som ikke eksisterer skal utløse IllegalArgument");
    }

    @Test
    @DisplayName("Tester det å iterere over place-objekter")
    void testIterator() {

        Iterator<Place> iterator = placeContainer.iterator();

        assertTrue(iterator.hasNext(),
                "Iterator av placeContainer skal returnere true, ettersom det er flere elementer igjen å itererer over.");

        Place placeReturnedFromIterator1 = iterator.next();

        assertEquals(placeReturnedFromIterator1, place1,
                "Iterator av places skal returnere place1");

        Place placeReturnedFromIterator2 = iterator.next();

        assertEquals(placeReturnedFromIterator2, place2,
                "Iterator av places skal returnere place2");

        assertFalse(iterator.hasNext(),
                "Iterator av placeContainer skal returnere false, ettersom ikke det er flere elementer igjen å itererer over.");
    }
}
