package bikeRentalApp.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.BikeRentalManager;
import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.UserContainer;
import bikeRentalApp.json.BikeRentalPersistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest extends ApplicationTest {

    private BikeRentalPersistence bikeRentalPersistence;

    private BikeRentalManager BRM;

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
        BRM = new BikeRentalManager();
        this.bikeRentalPersistence = new BikeRentalPersistence();
        userContainer = bikeRentalPersistence.readUserContainer();
        placeContainer = bikeRentalPersistence.readPlaceContainer();

        // placeContainer.addPlace("testPlaceGUI", 2);
        // placeContainer.findPlace("testPlaceGUI").addBike(new Bike("BIKE1234", "Fjellsykkel", "Rød"));

        // bikeRentalPersistence.writePlaceContainer(placeContainer);
    }

    // @Test
    // @Order(1)
    // public void testSignUp() {
    //     String username = "testUsername";
    //     String password = "testPassword1234";
    //     clickOn("#usernameInput").write(username);
    //     clickOn("#passwordInput").write(password);
    //     clickOn("#signUpButton");

    //     userContainer.addUser(controller.getLoggedInUser());

    //     assertNotNull(controller.getLoggedInUser(), "Ved innlogging i applikasjonen skal brukerobjektet være satt til innlogget bruker");

    //     clickOn("#selectDepartureLocation");
    // }

    // @Test
    // @Order(1)
    // public void testWrongSignUp() {
    //     String username = "testWrongUsername";
    //     String password = "testPassword";
    //     clickOn("#usernameInput").write(username);
    //     clickOn("#passwordInput").write(password);
    //     clickOn("#signUpButton");

    //     assertNull(controller.getLoggedInUser(), "Ved innlogging i applikasjonen må passordkravene stemme");
    //     FxAssert.verifyThat("OK", NodeMatchers.isVisible());

    // }
    
    // @Test
    // @Order(2)
    // public void testLogIn() {
    //     String username = "testUsername";
    //     String password = "testPassword1234";
    //     clickOn("#usernameInput").write(username);
    //     clickOn("#passwordInput").write(password);
    //     clickOn("#logInButton1");

    //     assertNotNull(controller.getLoggedInUser(), "Ved innlogging i applikasjonen skal brukerobjektet være satt til innlogget bruker");

    //     clickOn("#selectDepartureLocation");
    // }

    // @Test
    // @Order(2)
    // public void testWrongLogIn() {
    //     String username = "testWrongUsername";
    //     String password = "testPassword";
    //     clickOn("#usernameInput").write(username);
    //     clickOn("#passwordInput").write(password);
    //     clickOn("#logInButton1");


    //     assertNull(controller.getLoggedInUser(), "Ved innlogging i applikasjonen må passordkravene stemme");
    //     FxAssert.verifyThat("OK", NodeMatchers.isVisible());

    // }

    @Test
    @Order(3)
    public void testRentBike() {
        String username = "testUsername";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        clickOn("#selectDepartureLocation");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#listOfAvailableBikes");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#rentBikeButton");

        assertNotNull(controller.getLoggedInUser());
        assertEquals("BIKE1234", controller.getUserBike().getID());
    }

    @Test
    @Order(4)
    public void testDeliverBike() {
        String username = "testUsername";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        assertEquals("BIKE1234", controller.getUserBike().getID(), "Bruker skal allerede ha lånt en sykkel");

        clickOn("#returnBikeButton");
        clickOn("#selectArrivalLocation");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#confirmReturnBikeButton");

        assertNull(controller.getUserBike());
    }

    




    @AfterAll
    public void cleanDataBase() throws IOException {
        System.out.println("Deleting testfiles...");
        userContainer.removeUser("testUsername");
        placeContainer.removePlace("testPlaceGUI");

        bikeRentalPersistence.writeUserContainer(userContainer);
    }
}
