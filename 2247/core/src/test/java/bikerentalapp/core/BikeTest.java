package bikerentalapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BikeTest {

    private Bike bike;

    @BeforeEach
    public void setUp() {
        System.out.println("Initialiserer...");
        this.bike = new Bike("BIKE1234", "Terrengsykkel", "Blå");
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Bike-objekt med valide argumenter")
    public void testNewBike_validArguments() {
        new Bike("BIKE1234", "Terrengsykkel", "Blå");

        new Bike("BIKEBIKE", "Fjellsykkel", "Rosa");

        new Bike("12345678", "Elektrisk tandemsykkel", "Svart");
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Bike-objekt med ugyldige ID-argumenter")
    public void testNewBike_unvalidID() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike(null, "Terrengsykkel", "Blå");
        }, "IllegalArgument skal utløses om ID er 'null'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("", "Terrengsykkel", "Blå");
        }, "IllegalArgument skal utløses om ID er tom.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345", "Terrengsykkel", "Blå");
        }, "IllegalArgument skal utløses om ID er kortere enn 8 tegn.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345abc", "Terrengsykkel", "Blå");
        }, "IllegalArgument skal utløses om ID inneholder små bokstaver.");
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Bike-objekt med ugyldige type-argumenter")
    public void testNewBike_unvalidType() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345678", null, "Blå");
        }, "IllegalArgument skal utløses om typen er 'null'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345678", "Enhjulssykkel", "Blå");
        }, "IllegalArgument skal utløses om typen ikke eksisterer i listen 'validTypes' i Bike.java.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345678", "terrengsykkel", "Blå");
        }, "IllegalArgument skal utløses om typen ikke stemmer med store og små bokstaver.");
    }

    @Test
    @DisplayName("Tester opprettelsen av et nytt Bike-objekt med ugyldige colour-argumenter")
    public void testNewBike_unvalidColour() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345678", "Terrengsykkel", null);
        }, "IllegalArgument skal utløses om fargen er 'null'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345678", "Terrengsykkel", "Red");
        }, "IllegalArgument skal utløses om fargen ikke eksisterer i listen 'validColours' i Bike.java.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Bike("12345678", "Terrengsykkel", "blå");
        }, "IllegalArgument skal utløses om fargen ikke stemmer med store og små bokstaver.");
    }

    @Test
    @DisplayName("Tester at riktige verdier blir hentet ut av gettere")
    public void testGetters() {

        assertEquals("BIKE1234", bike.getId(), "getID skal returnere Bike1234");

        assertEquals("Terrengsykkel", bike.getType(), "getType skal returnere Terrengsykkel");

        assertEquals("Blå", bike.getColour(), "getColour skal returnere Blå");
    }

}
