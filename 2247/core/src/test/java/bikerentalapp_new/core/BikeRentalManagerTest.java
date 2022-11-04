package bikerentalapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.BikeRentalManager;
import bikeRentalApp.core.Place;
import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.UserContainer;
import bikeRentalApp.json.BikeRentalPersistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BikeRentalManagerTest {

    private BikeRentalManager BRM;

    private BikeRentalPersistence bikeRentalPersistence;

    private UserContainer userContainer;

    private PlaceContainer placeContainer;

    @BeforeEach
    public void setUp() throws IOException {
        System.out.println("Initialiserer BRM...");
        BRM = new BikeRentalManager();
        this.bikeRentalPersistence = new BikeRentalPersistence();
        userContainer = bikeRentalPersistence.readUserContainer();
        placeContainer = bikeRentalPersistence.readPlaceContainer();
    }

    @DisplayName("Tester om konstruktøren blir riktig opprettet")
    @Test
    public void testConstructorCorrectly() throws IOException {
        assertEquals(null, BRM.getLoggedInUser(), "Bruker skal være null før innlogging");
    }

    @DisplayName("Tester om bruker blir opprettet riktig")
    @Test
    public void testSignUp() throws IOException {
        BRM.signUp("testName", "testPassword123");

        userContainer.addUser(BRM.getLoggedInUser());
        bikeRentalPersistence.writeUserContainer(userContainer);

        assertEquals("testName", BRM.getLoggedInUser().getUsername(),
                "Brukernavnet til innlogget bruker skal være satt til testName");
        assertEquals("testPassword123", BRM.getLoggedInUser().getPassword(),
                "Passordet til innlogget bruker skal være testPassword123");

        assertEquals("testName", userContainer.findUser("testName").getUsername());
        assertEquals("testPassword123", userContainer.findUser("testName").getPassword());

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.signUp("testName", "testPassword123"));
        assertTrue(exception.getMessage().startsWith("Brukernavnet er tatt"),
                "Unntak skal utløses hvis en bruker allerede har registrert brukernavn");

    }

    @DisplayName("Tester om brukere kan logge inn med riktig innloggingsinformasjon")
    @Test
    public void testLogIn() throws IOException {
        BRM.signUp("testName2", "testPassword123");
        userContainer.addUser(BRM.getLoggedInUser());
        bikeRentalPersistence.writeUserContainer(userContainer);

        BRM.logIn("testName2", "testPassword123");

        assertEquals("testName2", BRM.getLoggedInUser().getUsername(),
                "Brukernavnet til innlogget bruker skal være satt til testName2");
        assertEquals("testPassword123", BRM.getLoggedInUser().getPassword(),
                "Passordet til innlogget bruker skal være testPassword123");

        assertEquals("testName2", userContainer.findUser("testName2").getUsername(),
                "Brukernavnet til bruker i persistens skal være satt til testName2");
        assertEquals("testPassword123", userContainer.findUser("testName2").getPassword(),
                "Passordet til bruker i persistens skal være testPassword123");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.logIn("testNameNotValid", "testPassword123"));
        assertTrue(exception.getMessage().startsWith("Brukernavn eller passord"),
                "Unntak skal utløses hvis passord eller brukernavn ikke stemmer med persistens");

        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.logIn("testName2", "testPasswordNotValid"));
        assertTrue(exception2.getMessage().startsWith("Brukernavn eller passord"),
                "Unntak skal utløses hvis passord eller brukernavn ikke stemmer med persistens");
    }

    @DisplayName("Tester om steder blir hentet riktig")
    @Test
    public void testGetPlaces() {
        placeContainer.addPlace("testPlace", 2);
        boolean isPresent = false;
        for (Place place : placeContainer.getPlaces()) {
            if (place.getName().equals("testPlace")) {
                isPresent = true;
            }
        }
        assertTrue(isPresent, "stedet skal eksistere");
    }

    @DisplayName("Tester om sykler blir lånt ut og levert riktig")
    @Test
    public void testRentBikeSystem() throws IOException {

        BRM.signUp("testName3", "testPassword123");

        userContainer.addUser(BRM.getLoggedInUser());
        userContainer.findUser(BRM.getLoggedInUser().getUsername()).setBike(null);
        bikeRentalPersistence.writeUserContainer(userContainer);

        assertEquals(null, BRM.getUserBike(), "før utlån skal bruker ikke ha noen registert sykkel");

        placeContainer.addPlace("testPlace2", 2);
        placeContainer.findPlace("testPlace2").addBike(new Bike("BIKE1234", "Fjellsykkel", "Rød"));
        bikeRentalPersistence.writePlaceContainer(placeContainer);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.rentBike("wrongPlaceName", "BIKE1234"));
        assertTrue(exception.getMessage().startsWith("Stedet"), "ved feil stedsnavn skal unntak utløses");

        BRM.rentBike("testPlace2", "BIKE1234");

        assertEquals("BIKE1234", BRM.getLoggedInUser().getBike().getID(),
                "utlånt sykkel skal stemme overens med registert sykkel");

        for (Place place : BRM.getPlaces()) {
            if (place.getName().equals("testPlace2")) {
                assertEquals(Arrays.asList(), place.getBikes(),
                        "ved utlånt skal utlånt sykkel ikke lenger være registert i det originale stedet");
            }
        }

        BRM.deliverBike("testPlace2");

        assertEquals(null, BRM.getLoggedInUser().getBike(), "ved innlevering skal sykkel fjernes fra bruker");

        for (Place place : BRM.getPlaces()) {
            if (place.getName().equals("testPlace2")) {
                assertEquals("BIKE1234", place.getBikes().get(0).getID(),
                        "ved innlevering til sted skal stedet oppdateres med den innleverte sykkelen");
            }
        }
    }

    @AfterAll
    public void cleanUp() throws IOException {
        System.out.println("Deleting testfiles...");
        userContainer.removeUser("testName");
        userContainer.removeUser("testName2");
        userContainer.removeUser("testName3");
        placeContainer.removePlace("testPlace");
        placeContainer.removePlace("testPlace2");
        bikeRentalPersistence.writeUserContainer(userContainer);
        bikeRentalPersistence.writePlaceContainer(placeContainer);
    }
}
