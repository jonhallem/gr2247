package bikeRentalApp.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.testfx.framework.junit5.ApplicationTest;

import bikeRentalApp.core.BikeRentalManager;
import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.UserContainer;
import bikeRentalApp.json.BikeRentalPersistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends ApplicationTest {
    /**
     * Rigorous Test :-)
     */

    private BikeRentalPersistence bikeRentalPersistence;

    private BikeRentalManager BRM;

    private UserContainer userContainer;

    private PlaceContainer placeContainer;
    

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("BikeRentalApp.fxml"));
        Parent parent = fxmlLoader.load();
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
    }

    @Test
    public void testSignUp() {
        String username = "testGUIName";
        String password = "testPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#signUpButton");

        userContainer.addUser(BRM.getLoggedInUser());
    }

    // @Test
    // public void testLogIn() {
    //     String username = "testGUIName";
    //     String password = "testPassword1234";
    //     clickOn("#usernameInput").write(username);
    //     clickOn("#passwordInput").write(password);
    //     clickOn("#logInButton");
    // }



    @AfterAll
    public void cleanDataBase() {
        System.out.println("Deleting testfiles...");
        userContainer.removeUser("testName");
    }
}
