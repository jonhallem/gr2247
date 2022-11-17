package bikerentalapp.ui;

import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.UserContainer;
import bikerentalapp.json.BikeRentalPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
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
public class ProfilePageTest extends ApplicationTest {

    private BikeRentalPersistence bikeRentalPersistence;

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
        this.bikeRentalPersistence = new BikeRentalPersistence();
        userContainer = bikeRentalPersistence.readUserContainer();
        placeContainer = bikeRentalPersistence.readPlaceContainer();
    }

    @DisplayName("Tester profilsiden")
    @Test
    @Order(1)
    public void testProfilePage() throws IOException {

        String username = "testUsernameGUI";
        String password = "testPassword1234";
        String newPassword = "newTestPassword1234";
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#signUpButton");

        // endrer view til bikeRentalProfilePage.fxml
        clickOn("#profileButton");

        clickOn("#changePasswordButton");
        clickOn("#abortChangePasswordButton");
        clickOn("#changePasswordButton");
        clickOn("#currentPasswordInput").write(password);
        clickOn("#newPasswordInput").write(newPassword);
        clickOn("#repeatNewPasswordInput").write(newPassword);
        clickOn("#confirmNewPasswordButton");

        clickOn("#logOutButton");

        // logger inn på nytt med oppdatert passord
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(newPassword);
        clickOn("#logInButton1");

        clickOn("#profileButton");
        clickOn("#backToMainMenuButton");
        // sjekker at den kommer seg tilbake til hovedskjermen
        clickOn("#selectDepartureLocation");
    }

    @DisplayName("Tester profilsiden med gammelt passord")
    @Test
    @Order(2)
    public void testProfilePageOldPassword() throws IOException {

        String username = "testUsernameGUI";
        String password = "testPassword1234";

        // skriver inn feil passord etter endring
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(password);
        clickOn("#logInButton1");

        FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    }

    @DisplayName("Tester profilsiden med samme passord")
    @Test
    @Order(2)
    public void testProfilePageSamePassword() throws IOException {

        String username = "testUsernameGUI";
        String newPassword = "newTestPassword1234";

        // skriver inn feil passord etter endring
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(newPassword);
        clickOn("#logInButton1");

        // endrer view til bikeRentalProfilePage.fxml
        clickOn("#profileButton");

        clickOn("#changePasswordButton");
        clickOn("#abortChangePasswordButton");
        clickOn("#changePasswordButton");
        // prøver å sette nytt passord til samme passord som tidligere
        clickOn("#currentPasswordInput").write(newPassword);
        clickOn("#newPasswordInput").write(newPassword);
        clickOn("#repeatNewPasswordInput").write(newPassword);
        clickOn("#confirmNewPasswordButton");

        FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    }

    @DisplayName("Tester profilsiden med feil passord")
    @Test
    @Order(2)
    public void testProfilePageWrongOldPassword() throws IOException {

        String username = "testUsernameGUI";
        String password = "wrongPassword1234";
        String newPassword = "newTestPassword1234";

        // skriver inn feil passord etter endring
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(newPassword);
        clickOn("#logInButton1");

        // endrer view til bikeRentalProfilePage.fxml
        clickOn("#profileButton");

        clickOn("#changePasswordButton");
        clickOn("#abortChangePasswordButton");
        clickOn("#changePasswordButton");
        // prøver å sette nytt passord til samme passord som tidligere
        clickOn("#currentPasswordInput").write(password);
        clickOn("#newPasswordInput").write(newPassword);
        clickOn("#repeatNewPasswordInput").write(newPassword);
        clickOn("#confirmNewPasswordButton");

        FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    }

    @DisplayName("Tester profilsiden med feilmeldinger under bytte til nytt passord")
    @Test
    @Order(3)
    public void testProfilePageWrongNewPasswords() throws IOException {

        String username = "testUsernameGUI";
        String newPassword = "newTestPassword1234";

        // skriver inn feil passord etter endring
        clickOn("#usernameInput").write(username);
        clickOn("#passwordInput").write(newPassword);
        clickOn("#logInButton1");

        // endrer view til bikeRentalProfilePage.fxml
        clickOn("#profileButton");

        clickOn("#changePasswordButton");
        clickOn("#abortChangePasswordButton");
        clickOn("#changePasswordButton");
        // repeterer ikke samme passord
        clickOn("#currentPasswordInput").write(newPassword);
        clickOn("#newPasswordInput").write("wrongPassword12345");
        clickOn("#repeatNewPasswordInput").write("wrongPassword123");
        clickOn("#confirmNewPasswordButton");

        FxAssert.verifyThat("OK", NodeMatchers.isVisible());
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