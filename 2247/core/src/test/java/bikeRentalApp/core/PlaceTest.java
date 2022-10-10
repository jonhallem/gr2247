package bikeRentalApp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class PlaceTest {

    private List<Bike> listOfBikes;
    private Place place;


    @BeforeEach
    public void setUp() {

        Bike bike1 =  new Bike("BIKE1234", "Terrengsykkel", "Blå");

        Bike bike2 = new Bike("BIKEBIKE", "Fjellsykkel", "Rosa");

        this.listOfBikes = new ArrayList<>(Arrays.asList(bike1, bike2));

        this.place = new Place("Gløshaugen", 10, this.listOfBikes);

    }
    

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med valide argumenter")
    public void testNewPlace_validArguments() {
        
        Place place1 = new Place("Bymarka", 1);

        Place place2 = new Place("Bymarka1", 120);

        Place place3 = new Place("bymarka", 40);

        Place place4 = new Place("Gløshaugen", 30, this.listOfBikes);
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige name-argumenter")
    public void testNewPlace_unvalidName() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Place(null, 3); }, "IllegalArgument skal utløses om name er 'null'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("", 3); }, "IllegalArgument skal utløses om name er en tom streng.");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Place("  ", 3); }, "IllegalArgument skal utløses om name er en streng bestående av kun mellomrom.");            
    }


    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige maximumNumberOfBikes-argumenter")
    public void testNewPlace_unvalidMaximumNumberOfBikes() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Place("Bymarka", 0); }, "IllegalArgument skal utløses om maximumNumberOfBikes er mindre enn 1.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("Bymarka", -20); }, "IllegalArgument skal utløses om maximumNumberOfBikes er mindre enn 1.");
    }


    @Test
    @DisplayName("Tester opprettelsen av et nytt Place-objekt med ugyldige bikes-argumenter")
    public void testNewPlace_unvalidBikes() {
        
        assertThrows(IllegalStateException.class, () -> {
            new Place("Bymarka", 1, this.listOfBikes); }, "IllegalArgument skal utløses om maximumNumberOfBikes er mindre enn antallet sykler som finnes i bikes-listen.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Place("Bymarka", 20, null); }, "IllegalArgument skal utløses om bikes er null.");
    }


    @Test
    @DisplayName("Tester at riktige verdier blir hentet ut av gettere")
    public void testGetters() {
        
        assertEquals("Gløshaugen", this.place.getName());

        assertEquals(10, this.place.getMaximumNumberOfBikes());

        for (int i = 0; i < this.listOfBikes.size(); i++) {
            assertEquals(this.listOfBikes.get(i), this.place.getBikes().get(i));
        }
        
    }


}
