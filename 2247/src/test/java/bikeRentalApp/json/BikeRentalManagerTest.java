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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import bikeRentalApp.core.BikeRentalManager;
import bikeRentalApp.core.UserContainer;
import bikeRentalApp.json.BikeRentalPersistence;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BikeRentalManagerTest {

    private BikeRentalManager BRM;

    private BikeRentalPersistence bikeRentalPersistence;

    private UserContainer userContainer;
    
    @BeforeEach
    public void setUp() throws IOException {
        System.out.println("Initialiserer BRM...");
        BRM = new BikeRentalManager();
        this.bikeRentalPersistence = new BikeRentalPersistence();
        userContainer = bikeRentalPersistence.readUserContainer();
    }
    
    @Test
    public void testConstructorCorrectly() throws IOException {
       assertEquals(null, BRM.getLoggedInUser());
    }

    @Test
    public void testSignUp() throws IOException {
       BRM.signUp("testName", "testPassword123");

       assertEquals("testName", BRM.getLoggedInUser().getUsername());
       assertEquals("testPassword123", BRM.getLoggedInUser().getPassword());

       Exception exception = Assertions.assertThrows(IllegalArgumentException.class, 
       () -> BRM.signUp("testName", "testPassword123"));
       assertTrue(exception.getMessage().startsWith("Brukernavnet er tatt"));

    }

    @AfterAll
    public void cleanUp() throws IOException {
        System.out.println("Deleting testfiles...");
        userContainer.removeUser("testName");
        bikeRentalPersistence.writeUserContainer(userContainer);
    }
}
