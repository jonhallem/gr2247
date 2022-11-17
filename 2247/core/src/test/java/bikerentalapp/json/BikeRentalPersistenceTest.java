package bikerentalapp.json;

import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;
import bikerentalapp.core.Bike;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BikeRentalPersistenceTest {

    private BikeRentalPersistence bikeRentalPersistence;

    private PlaceContainer placeContainerToWriteBackAfterTests;

    private UserContainer userContainerToWriteBackAfterTests;

    @BeforeAll
    public void setUp() throws IOException {
        this.bikeRentalPersistence = new BikeRentalPersistence();
        this.placeContainerToWriteBackAfterTests = this.bikeRentalPersistence.readPlaceContainer();
        this.userContainerToWriteBackAfterTests = this.bikeRentalPersistence.readUserContainer();

    }

    @DisplayName("Tester skriving og lesing av PlaceContainer")
    @Test
    public void testWriteAndReadPlaceContainer() {
        PlaceContainer placeContainerToWrite = new PlaceContainer(new ArrayList<>());
        placeContainerToWrite.addPlace("Somewhere", 20);
        placeContainerToWrite.addPlace("Nowhere", 30);
        placeContainerToWrite.findPlace("Nowhere").addBike(new Bike("BIKE1234", "Terrengsykkel", "Blå"));

        try {
            this.bikeRentalPersistence.writePlaceContainer(placeContainerToWrite);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        PlaceContainer placeContainerToRead = null;
        try {
            placeContainerToRead = this.bikeRentalPersistence.readPlaceContainer();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertEquals("Somewhere", placeContainerToRead.findPlace("Somewhere").getName(),
                "Et sted med navnet Somewhere skal finnes i PlaceContaineren etter skriving og lesing.");
        assertEquals(20, placeContainerToRead.findPlace("Somewhere").getMaximumNumberOfBikes(),
                "MaximumNumberOfBikes på Somewhere skal være 20 etter skriving og lesing.");
        assertEquals(0, placeContainerToRead.findPlace("Somewhere").getBikes().size(),
                "Det skal ikke være noen sykler lagret i stedet Somewhere etter skriving og lesing.");

        assertEquals("Nowhere", placeContainerToRead.findPlace("Nowhere").getName(),
                "Et sted med navnet Nowhere skal finnes i PlaceContaineren etter skriving og lesing.");
        assertEquals(30, placeContainerToRead.findPlace("Nowhere").getMaximumNumberOfBikes(),
                "MaximumNumberOfBikes på Nowhere skal være 30 etter skriving og lesing.");
        assertEquals(1, placeContainerToRead.findPlace("Nowhere").getBikes().stream()
                .filter(bike -> bike.getId().equals("BIKE1234")).count(),
                "Det skal være en sykkel lagret i stedet Nowhere med ID BIKE1234 etter skriving og lesing.");
    }

    @DisplayName("Tester skriving og lesing av UserContainer")
    @Test
    public void readAndWriteUserContainer() {
        UserContainer userContainerToWrite = new UserContainer(new ArrayList<>());
        User user = new User("Someone", "biker1");
        userContainerToWrite.addUser(user);

        try {
            this.bikeRentalPersistence.writeUserContainer(userContainerToWrite);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        UserContainer userContainerToRead = null;
        try {
            userContainerToRead = this.bikeRentalPersistence.readUserContainer();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals("Someone",
                userContainerToRead.getUsers().stream()
                        .filter(userInContainer -> userInContainer.getUsername().equals("Someone")).findFirst()
                        .get().getUsername(),
                "En bruker med navnet Someone skal finnes i UserContainer etter skriving og lesing.");
    }

    @DisplayName("Tester at JSON-fil blir opprettet om den ikke finnes")
    @Test
    public void testEnsureSaveFileExists() {
        File placeFile = Path.of(System.getProperty("user.home"), "BikeRentalApp").resolve("places.json").toFile();
        placeFile.delete();
        try {
            this.bikeRentalPersistence.readPlaceContainer();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertTrue(placeFile.exists(), "places.json skal være opprettet etter kall på readPlaceContainer().");

        File userFile = Path.of(System.getProperty("user.home"), "BikeRentalApp").resolve("users.json").toFile();
        userFile.delete();

        try {
            this.bikeRentalPersistence.readUserContainer();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertTrue(userFile.exists(), "users.json skal være opprettet etter kall på readPlaceContainer().");
    }

    @DisplayName("Tester henting av ObjectMapper")
    @Test
    public void testGetObjectMapper() {
        this.bikeRentalPersistence.getObjectMapper();
    }

    @AfterAll
    public void cleanDataBase() throws IOException {
        this.bikeRentalPersistence.writeUserContainer(this.userContainerToWriteBackAfterTests);
        this.bikeRentalPersistence.writePlaceContainer(this.placeContainerToWriteBackAfterTests);
    }

}