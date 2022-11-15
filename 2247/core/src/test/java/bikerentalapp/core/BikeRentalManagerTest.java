package bikerentalapp.core;

import bikerentalapp.json.BikeRentalPersistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BikeRentalManagerTest {

    private BikeRentalManager BRM;

    private BikeRentalPersistence bikeRentalPersistence;

    private UserContainer userContainer;

    private PlaceContainer placeContainer;

    @BeforeEach
    public void setUp() throws IOException {
        BRM = new BikeRentalManager();
        this.bikeRentalPersistence = BRM.getBikeRentalPersistence();
    }

    @DisplayName("Tester om konstruktøren fungerer riktig")
    @Test
    public void testConstructorCorrectly() throws IOException {
        assertTrue(BRM.getBikeRentalPersistence() instanceof BikeRentalPersistence,
                "bikeRentalPersistence skal være satt ved instansiering av BRM");
    }

    @DisplayName("Tester om steder blir hentet riktig")
    @Test
    @Order(1)
    public void testGetPlaces() throws IOException {

        List<String> correctPlaceList = new ArrayList<>(
                Arrays.asList("Bymarka", "Munkholmen", "Lerkendal", "Tiller", "Nidarosdomen", "Gløshaugen", "Lilleby",
                        "testPlace", "testPlace2"));
        List<Place> returnedPlaceList = BRM.getPlaceContainer().getPlaces();

        for (Place place : returnedPlaceList) {
            if (!correctPlaceList.contains(place.getName())) {
                fail("Den returnerte lista skal kun inneholde de stedene som er satt av utviklerne.");
            }
        }
    }

    @DisplayName("Tester om bruker blir opprettet riktig")
    @Test
    @Order(2)
    public void testSignUp() throws IOException {

        User newUser = BRM.signUp("testName", "testPassword123");

        assertEquals("testName", newUser.getUsername(),
                "Brukernavnet til den nye brukeren skal være 'testName'");
        assertEquals("testPassword123", newUser.getPassword(),
                "Passordet til den nye brukeren skal være 'testPassword123'");

        this.userContainer = this.bikeRentalPersistence.readUserContainer();
        User newUserFromUserContainer = this.userContainer.findUser("testName");

        assertEquals("testName", newUserFromUserContainer.getUsername(),
                "Brukeren skal bli skrevet til persistens og brukernavnet skal være 'testName'");
        assertEquals("testPassword123", newUserFromUserContainer.getPassword(),
                "Brukeren skal bli skrevet til persistens og passordet skal være 'testPassword123'");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.signUp("testName", "testPassword123"));
        assertTrue(exception.getMessage().startsWith("Brukernavnet er tatt"),
                "Unntak skal utløses hvis en bruker allerede har registrert brukernavn");

    }

    @DisplayName("Tester om brukere kan logge inn med riktig innloggingsinformasjon")
    @Test
    @Order(3)
    public void testLogIn() throws IOException {

        BRM.signUp("testName2", "testPassword123");

        User loggedInUser = BRM.logIn("testName2", "testPassword123");

        assertEquals("testName2", loggedInUser.getUsername(),
                "Brukernavnet til brukeren som er logget inn skal være 'testName2'");
        assertEquals("testPassword123", loggedInUser.getPassword(),
                "Passordet til brukeren som er logget inn skal være 'testPassword123'");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.logIn("testNameNotValid", "testPassword123"));
        assertTrue(exception.getMessage().startsWith("Brukernavn eller passord"),
                "Unntak skal utløses hvis passord eller brukernavn ikke stemmer med persistens");

        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.logIn("testName2", "testPasswordNotValid"));
        assertTrue(exception2.getMessage().startsWith("Brukernavn eller passord"),
                "Unntak skal utløses hvis passord eller brukernavn ikke stemmer med persistens");
    }

    @DisplayName("Tester om sykler blir lånt ut og levert riktig")
    @Test
    @Order(4)
    public void testRentAndDeliverBike() throws IOException {

        this.placeContainer = BRM.getBikeRentalPersistence().readPlaceContainer();
        this.placeContainer.addPlace("testPlace2", 2);
        Bike bikeToRent = new Bike("BIKY1234", "Fjellsykkel", "Rød");
        this.placeContainer.findPlace("testPlace2").addBike(bikeToRent);
        this.bikeRentalPersistence.writePlaceContainer(placeContainer);
        Place validPlace = placeContainer.findPlace("testPlace2");
        Place invalidPlace = new Place("Nowhere", 100);

        User loggedInUser = BRM.signUp("testName3", "testPassword123");

        assertEquals(null, loggedInUser.getBike(),
                "før utlån skal bruker ikke en nyregistrert bruker ha noen registert sykkel");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> BRM.rentBike(invalidPlace.getName(), bikeToRent.getId(), loggedInUser.getUsername()));
        assertTrue(exception.getMessage().equals("Et sted med dette navnet finnes ikke"),
                "ved feil stedsnavn skal unntak utløses");

        BRM.rentBike(validPlace.getName(), bikeToRent.getId(), loggedInUser.getUsername());

        this.userContainer = this.bikeRentalPersistence.readUserContainer();
        User loggedInUserInUserContainer = userContainer.findUser(loggedInUser.getUsername());

        assertEquals("BIKY1234", loggedInUserInUserContainer.getBike().getId(),
                "utlånt sykkel hos den innloggedebrukeren i persistens skal stemme overens med registert sykkel");

        placeContainer = this.bikeRentalPersistence.readPlaceContainer();

        assertFalse(placeContainer.findPlace(validPlace.getName()).getBikes().contains(bikeToRent),
                "Utlånt sykkel skal ikke lenger finnes i stedet i placecontainer som tilsvarer stedet der den ble leid fra");

        BRM.deliverBike(loggedInUser.getUsername(), validPlace.getName());

        User loggedInUserFromContainer = this.bikeRentalPersistence.readUserContainer().findUser("testName3");

        assertEquals(null, loggedInUserFromContainer.getBike(), "ved innlevering skal sykkel fjernes fra bruker");

        userContainer = this.bikeRentalPersistence.readUserContainer();
        loggedInUserInUserContainer = userContainer.findUser(loggedInUser.getUsername());

        assertEquals(null, loggedInUserInUserContainer.getBike(), "ved innlevering skal sykkel fjernes fra bruker");

        this.placeContainer = this.bikeRentalPersistence.readPlaceContainer();
        Place validPlaceFromPlaceContainer = placeContainer.findPlace(validPlace.getName());

        assertTrue(
                validPlaceFromPlaceContainer.getBikes().stream().filter(bike -> bike.getId().equals("BIKY1234"))
                        .count() == 1,
                "Utleiestedet skal ha fått tilbake sykkelen.");

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
