package bikerentalapp.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.UserContainer;
import bikeRentalApp.json.BikeRentalPersistence;
import bikerentalapp_new.ui.BikeRentalAppController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest extends ApplicationTest {

    private BikeRentalPersistence bikeRentalPersistence;

    private UserContainer userContainer;

    private PlaceContainer placeContainer;

    private BikeRentalAppController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("BikeRentalApp.fxml"));
        Parent parent = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws IOException {
        System.out.println("Initialiserer BRMController...");
        this.bikeRentalPersistence = new BikeRentalPersistence();
        userContainer = bikeRentalPersistence.readUserContainer();
        placeContainer = bikeRentalPersistence.readPlaceContainer();
    }

    @DisplayName("Tester oppretting av bruker og opprettet sted for videre testing")
    @Test
    @Order(1)
    public void testSignUp() throws IOException {
        String username = "testUsernameGUI";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#signUpButton");

        placeContainer.addPlace("testPlaceGUI", 2);
        placeContainer.findPlace("testPlaceGUI").addBike(new Bike("BIKE1234", "Fjellsykkel", "Rød"));
        bikeRentalPersistence.writePlaceContainer(placeContainer);

        userContainer.addUser(controller.getLoggedInUser());

        assertNotNull(controller.getLoggedInUser(),
                "Ved innlogging i applikasjonen skal brukerobjektet være satt til innlogget bruker");
    }

    @DisplayName("Tester oppretting av bruker med feil krav")
    @Test
    @Order(1)
    public void testWrongSignUp() {
        String username = "testWrongUsernameGUI";
        String password = "testPassword";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#signUpButton");

        assertNull(controller.getLoggedInUser(), "Ved innlogging i applikasjonen må passordkravene stemme");
        FxAssert.verifyThat("OK", NodeMatchers.isVisible());

    }

    @DisplayName("Tester innlogging av allerede etablert bruker")
    @Test
    @Order(2)
    public void testLogIn() {
        String username = "testUsernameGUI";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        assertNotNull(controller.getLoggedInUser(),
                "Ved innlogging i applikasjonen skal brukerobjektet være satt til innlogget bruker");
    }

    @DisplayName("Tester innlogging av bruker med feil innloggingsinformasjon")
    @Test
    @Order(2)
    public void testWrongLogIn() {
        String username = "testWrongUsernameGUI";
        String password = "testPassword";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        assertNull(controller.getLoggedInUser(), "Ved innlogging i applikasjonen må passordkravene stemme");
        FxAssert.verifyThat("OK", NodeMatchers.isVisible());

    }

    @DisplayName("Tester utlån av sykkel fra teststed")
    @Test
    @Order(3)
    public void testThrowsDuringRentBike() {
        String username = "testUsernameGUI";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        // riktig sted vil alltid ligge nederst under testing
        clickOn("#selectDepartureLocation");
        for (int i = 0; i < this.placeContainer.getPlaces().size() + 1; i++) {
            type(KeyCode.DOWN);
        }
        type(KeyCode.ENTER);
        // Unngår å velge et sted
        clickOn("#rentBikeButton");
        FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    }

    @DisplayName("Tester utlån av sykkel fra teststed")
    @Test
    @Order(4)
    public void testRentBike() {
        String username = "testUsernameGUI";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        // riktig sted vil alltid ligge nederst under testing
        clickOn("#selectDepartureLocation");
        for (int i = 0; i < this.placeContainer.getPlaces().size() + 1; i++) {
            type(KeyCode.DOWN);
        }
        type(KeyCode.ENTER);
        clickOn("#listOfAvailableBikes");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#rentBikeButton");

        assertNotNull(controller.getLoggedInUser(), "Innlogget bruker skal være registrert");
        assertEquals("BIKE1234", controller.getUserBike().getID(),
                "Bruker skal ha registrert utlånt sykkel som ID: 'BIKE1234'");
    }

    @DisplayName("Tester innlevering av sykkel til teststed")
    @Test
    @Order(5)
    public void testThrowsDuringDeliverBike() {
        String username = "testUsernameGUI";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        // riktig sted vil alltid ligge nederst under testing
        clickOn("#returnBikeButton");
        // Unngår å velge innleverings-sted
        clickOn("#confirmReturnBikeButton");
        FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    }

    @DisplayName("Tester innlevering av sykkel til teststed")
    @Test
    @Order(6)
    public void testDeliverBike() {
        String username = "testUsernameGUI";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        assertEquals("BIKE1234", controller.getUserBike().getID(),
                "Bruker skal allerede ha lånt en sykkel med ID 'BIKE1234'");

        // riktig sted vil alltid ligge nederst under testing
        clickOn("#returnBikeButton");
        clickOn("#selectArrivalLocation");
        for (int i = 0; i < this.placeContainer.getPlaces().size() + 1; i++) {
            type(KeyCode.DOWN);
        }
        type(KeyCode.ENTER);
        clickOn("#confirmReturnBikeButton");

        assertNull(controller.getUserBike(), "Sykkel skal være innlevert og ikke registrert på bruker");
    }

    @AfterAll
    public void cleanDataBase() throws IOException {
        System.out.println("Deleting testfiles...");
        userContainer.removeUser("testUsernameGUI");
        placeContainer.removePlace("testPlaceGUI");

        bikeRentalPersistence.writeUserContainer(userContainer);
        bikeRentalPersistence.writePlaceContainer(placeContainer);
    }
}