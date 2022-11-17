package bikerentalapp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaceTest {

    private List<Bike> listOfBikes;
    private Place place;
    private Bike bike1;
    private Bike bike2;

    @BeforeEach
    public void setUp() {

        this.bike1 = new Bike("BIKE1234", "Terrengsykkel", "Blå");

        this.bike2 = new Bike("BIKEBIKE", "Fjellsykkel", "Rosa");

        this.listOfBikes = new ArrayList<>(Arrays.asList(bike1, bike2));

        this.place = new Place("Gløshaugen", 3, this.listOfBikes);

    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med valide argumenter")
    public void testNewPlace_validArguments() {

        new Place("Bymarka", 1);

        new Place("Bymarka1", 120);

        new Place("bymarka", 40);

        new Place("Gløshaugen", 30, this.listOfBikes);
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige name-argumenter")
    public void testNewPlace_unvalidName() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Place(null, 3);
        }, "IllegalArgumentException skal utløses om name er 'null'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("", 3);
        }, "IllegalArgumentException skal utløses om name er en tom streng.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("  ", 3);
        }, "IllegalArgumentException skal utløses om name er en streng bestående av kun mellomrom.");
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige maximumNumberOfBikes-argumenter")
    public void testNewPlace_unvalidMaximumNumberOfBikes() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("Bymarka", 0);
        }, "IllegalArgumentException skal utløses om maximumNumberOfBikes er mindre enn 1.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("Bymarka", -20);
        }, "IllegalArgumentException skal utløses om maximumNumberOfBikes er mindre enn 1.");
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige bikes-argumenter")
    public void testNewPlace_unvalidBikes() {

        assertThrows(IllegalStateException.class, () -> {
            new Place("Bymarka", 1, this.listOfBikes);
        }, "IllegalArgumentException skal utløses om maximumNumberOfBikes er mindre enn antallet sykler som finnes i bikes-listen.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("Bymarka", 20, null);
        }, "IllegalArgumentException skal utløses om bikes er null.");
    }

    @Test
    @DisplayName("Tester at riktige verdier blir hentet ut av gettere")
    public void testGetters() {

        assertEquals("Gløshaugen", this.place.getName(), "Skal returnere strengen 'Gløshaugen'.");

        assertEquals(3, this.place.getMaximumNumberOfBikes(), "Skal returnere int-en 3.");

        for (int i = 0; i < this.listOfBikes.size(); i++) {
            assertEquals(this.listOfBikes.get(i), this.place.getBikes().get(i),
                    "Bike på plass i i getBikes() skal være lik bike på plass i i listOfBikes.");
        }

    }

    @Test
    @DisplayName("Tester at sykkel blir fjernet og returnert")
    public void testRemoveAndGetBike_validBikeID() {

        assertEquals(this.bike1, this.place.removeAndGetBike("BIKE1234"));

        assertEquals(this.bike2, this.place.removeAndGetBike("BIKEBIKE"));

        assertTrue(this.place.getBikes().isEmpty(),
                "getBikes() skal returnere en tom liste når alle sykler er fjernet.");
    }

    @Test
    @DisplayName("Tester at unntak blir utløst ved feil i bikeID")
    public void testRemoveAndGetBike_unvalidBikeID() {

        assertThrows(IllegalArgumentException.class, () -> {
            this.place.removeAndGetBike(null);
        }, "IllegalArgumentException skal utløses om bikeID er null.");

        assertThrows(IllegalArgumentException.class, () -> {
            this.place.removeAndGetBike("BIKE2s");
        }, "IllegalArgumentException skal utløses om bikeID har feil format.");

    }

    @Test
    @DisplayName("Tester at unntak blir utløst når sykkelen ikke finnes i place")
    public void testRemoveAndGetBike_bikeNotFound() {

        assertThrows(IllegalArgumentException.class, () -> {
            this.place.removeAndGetBike("BIKE9876");
        }, "IllegalArgumentException skal utløses om det ikke finnes en sykkel med angitt ID.");

    }

    @Test
    @DisplayName("Tester at sykkel blir lagt til")
    public void testAddBike_validInput() {

        Bike bike3 = new Bike("BIKE9876", "Terrengsykkel", "Blå");

        this.place.addBike(bike3);

        assertTrue(this.place.getBikes().contains(bike3),
                "bike3 skal finnes i listen som returneres av getBikes() etter den har blitt  lagt til.");

    }

    @Test
    @DisplayName("Tester at unntak blir utløst når man forsøker å legge til 'null' som en ny sykkel")
    public void testAddBike_unvalidInput() {

        assertThrows(IllegalArgumentException.class, () -> {
            this.place.addBike(null);
        }, "IllegalArgumentException skal utløses om input er null");

        assertFalse(this.place.getBikes().contains(null),
                "Listen over bikes i place skal ikke kunne inneholde 'null'.");

    }

    @Test
    @DisplayName("Tester at unntak blir utløst når stedet er fullt og man fosøker å legge til en ny sykkel")
    public void testAddBike_placeFull() {

        Bike bike3 = new Bike("BIKE9876", "Terrengsykkel", "Blå");

        Bike bike4 = new Bike("BIKE6543", "Tandemsykkel", "Svart");

        this.place.addBike(bike3);

        assertThrows(IllegalStateException.class, () -> {
            this.place.addBike(bike4);
        }, "IllegalStateException skal utløses om stedet er fullt");

        assertFalse(this.place.getBikes().contains(bike4),
                "Listen over bikes i place skal ikke kunne inneholde et nytt sykkelobjekt etter at maks antall sykler er nådd.");

    }

    @Test
    @DisplayName("Tester iteratoren")
    public void testIterator() {

        Place place2 = new Place("Dragvoll", 6);

        assertFalse(place2.iterator().hasNext(),
                "Iterator av bikes i place skal ikke ha noe neste når det ikke er noen sykler lagt til.");

        assertTrue(place.iterator().hasNext(),
                "Iterator av bikes i place skal returnere true, i og med det er minst en bike i listen.");

        assertEquals(place.iterator().next(), bike1,
                "Iterator av bikes i place skal returnere bike1");

    }

}
