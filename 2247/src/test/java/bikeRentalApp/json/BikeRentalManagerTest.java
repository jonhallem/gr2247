package bikeRentalApp.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.BikeRentalManager;
import bikeRentalApp.core.Place;
import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.UserContainer;


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
    
    @Test
    public void testConstructorCorrectly() throws IOException {
       assertEquals(null, BRM.getLoggedInUser());
    }

    @Test
    public void testSignUp() throws IOException {
       BRM.signUp("testName", "testPassword123");

       userContainer.addUser(BRM.getLoggedInUser());
       bikeRentalPersistence.writeUserContainer(userContainer);

       assertEquals("testName", BRM.getLoggedInUser().getUsername());
       assertEquals("testPassword123", BRM.getLoggedInUser().getPassword());

       assertEquals("testName", userContainer.findUser("testName").getUsername());
       assertEquals("testPassword123", userContainer.findUser("testName").getPassword());

       Exception exception = Assertions.assertThrows(IllegalArgumentException.class, 
       () -> BRM.signUp("testName", "testPassword123"));
       assertTrue(exception.getMessage().startsWith("Brukernavnet er tatt"));

    }

    @Test
    public void testLogIn() throws IOException {
        BRM.signUp("testName2", "testPassword123");
        userContainer.addUser(BRM.getLoggedInUser());
        bikeRentalPersistence.writeUserContainer(userContainer);

        BRM.logIn("testName2", "testPassword123");

        assertEquals("testName2", BRM.getLoggedInUser().getUsername());
        assertEquals("testPassword123", BRM.getLoggedInUser().getPassword());

        assertEquals("testName2", userContainer.findUser("testName2").getUsername());
        assertEquals("testPassword123", userContainer.findUser("testName2").getPassword());

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, 
        () -> BRM.logIn("testNameNotValid", "testPassword123"));
        assertTrue(exception.getMessage().startsWith("Brukernavn eller passord"));

        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class, 
        () -> BRM.logIn("testName2", "testPasswordNotValid"));
        assertTrue(exception2.getMessage().startsWith("Brukernavn eller passord"));
    }


    @Test
    public void testGetPlaces() {
        placeContainer.addPlace("testPlace", 2);
        boolean isPresent = false;
        for (Place place : placeContainer.getPlaces()) {
            if (place.getName().equals("testPlace")) {
                isPresent = true;
            }
        }
        assertTrue(isPresent);
    }

    @Test
    public void testRentBikeSystem() throws IOException {

     BRM.signUp("testName3", "testPassword123");

        userContainer.addUser(BRM.getLoggedInUser());
        userContainer.findUser(BRM.getLoggedInUser().getUsername()).setBike(null);
        bikeRentalPersistence.writeUserContainer(userContainer);

        assertEquals(null, BRM.getUserBike());

        placeContainer.addPlace("testPlace2", 2);
        placeContainer.findPlace("testPlace2").addBike(new Bike("BIKE1234", "Fjellsykkel", "Rød"));
        bikeRentalPersistence.writePlaceContainer(placeContainer);

        BRM.rentBike("testPlace2", "BIKE1234");

        assertEquals("BIKE1234", BRM.getLoggedInUser().getBike().getID());

        for (Place place : BRM.getPlaces()) {
            if (place.getName().equals("testPlace2")) {
                assertEquals(Arrays.asList(), place.getBikes());
            }
        }

        BRM.deliverBike("testPlace2");

        assertEquals(null, BRM.getLoggedInUser().getBike());

        for (Place place : BRM.getPlaces()) {
            if (place.getName().equals("testPlace2")) {
                assertEquals("BIKE1234", place.getBikes().get(0).getID());
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
