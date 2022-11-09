package bikerentalapp.ui;

import bikerentalapp.core.User;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A class for the controller of the profile page in Bike Rental App.
 * Defines functions that are called when interacting with the GUI
 * on the apps profile page, thus binding the GUI and the model.
 */
public class ProfilePageController {

    // -------------- Felt -----------------

    private User loggedInUser = null;

    private BikeRentalManagerAccess bikeRentalManagerAccess;

    private Scene mainMenuScene = null;

    // -------------- FXML-elementer i header -----------------

    @FXML
    private Button backToMainMenuButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Label usernameTitle;

    // -------------- FXML-elementer relatert til endring av passord -----------------

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button abortChangePasswordButton;

    @FXML
    private Button confirmNewPasswordButton;

    @FXML
    private Pane changePasswordPane;

    @FXML
    private PasswordField currentPasswordInput;

    @FXML
    private PasswordField newPasswordInput;

    @FXML
    private PasswordField repeatNewPasswordInput;

    // -------------- FXML-elementer relatert til leiehistorikk av passord -----------------

    @FXML
    private ListView<String> listOfRentalHistory;

    // ----------- Initialisering -------------

    @FXML
    void initialize() {

        // TODO: Hvis utleiehistorikk skal implementeres:
        // updateRentalHistory();

        this.bikeRentalManagerAccess = new DirectBikeRentalManagerAccess();
    }

    /**
     * Sets the logged in user when the view is changed from main menu to profile
     * page.
     *
     * @param loggedInUser the {@code User} object currently logged in.
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.updateUserName();
    }

    // ----------- Metoder for endring av passord -------------

    /**
     * Shows a pane to change the logged in user's password in the view.
     */
    @FXML
    private void showChangePasswordPane() {
        this.changePasswordPane.setVisible(true);
    }

    /**
     * Hides the pane to change the user's password.
     */
    @FXML
    private void hideChangePasswordPane() {
        this.changePasswordPane.setVisible(false);
        this.currentPasswordInput.clear();
        this.newPasswordInput.clear();
        this.repeatNewPasswordInput.clear();
    }

    /**
     * Takes the input strings from the fields in the change-user-password-pane,
     * validates them, and sets the new, given password if the validation succeeds.
     * If the validation does not succeed, shows an error message.
     */
    @FXML
    private void confirmNewPassword() {
        String currentPassword = this.currentPasswordInput.getText();
        String newPassword = this.newPasswordInput.getText();
        String newPasswordRepeated = this.repeatNewPasswordInput.getText();

        if (this.loggedInUser != null) {
            if (!currentPassword.equals(this.loggedInUser.getPassword())) {
                this.errorMessage("Nåværende passord er ikke riktig.");
            } else if (this.loggedInUser.getPassword().equals(newPassword)) {
                this.errorMessage("Det nye passordet kan ikke være likt det gamle passordet");
            }else if (!newPassword.equals(newPasswordRepeated)) {
                this.errorMessage(
                        "Passordene i feltet \"Nytt passord\" og feltet \"Gjenta nytt passord\" "
                                + "stemmer ikke overens.");
            } else {
                try {
                    this.loggedInUser = this.bikeRentalManagerAccess
                            .setUserPassword(this.loggedInUser, newPassword);
                    this.hideChangePasswordPane();
                } catch (IllegalArgumentException | IOException e) {
                    this.errorMessage(e.getMessage());
                }
            }
        } else {
            this.errorMessage("Feil: BikeRentalManager er \"null\".");
        }

    }

    // ----------- Metode for å logge ut ----------------

    /**
     * Logs out the current signed in user and returns the view to the login view.
     */
    @FXML
    private void logOut() {
        try {
            ((Stage) usernameTitle.getScene().getWindow()).setScene(getMainMenuScene(null));
        } catch (Exception e) {
            System.err.println("Couldn't load log in scene");
            e.getCause().printStackTrace();
        }
    }

    // ----------- Metode for å gå tilbake til hovedvinduet ----------------

    /**
     * Sets the view to main menu, keeping the bikeRentalManager object (ensures
     * that the user is still logged in).
     */
    @FXML
    void backToMainMenu() {
        try {
            ((Stage) usernameTitle.getScene().getWindow()).setScene(
                    getMainMenuScene(this.loggedInUser));
        } catch (Exception e) {
            System.err.println("Couldn't load main menu scene");
            e.getCause().printStackTrace();
        }
    }

    // ----------- Hjelpe metoder ----------------

    /**
     * Sets the username on the profile page in accordence with the logged in user.
     */
    private void updateUserName() {
        if (this.loggedInUser != null) {
            this.usernameTitle.setText("Bruker: " + this.loggedInUser.getUsername());
        }
    }

    /**
     * Creates and returns the main menu scene, used to set the view.
     *
     * @param loggedInUser the {@code User} object currently in use. Should be
     *                     {@code null} if user should be logged out.
     * @return Scene the {@code Scene} object to set the view to.
     * @throws RuntimeException if an IOExceprion happens when the fxmlLoader is
     *                          loaded.
     */
    private Scene getMainMenuScene(User loggedInUser) throws RuntimeException {
        if (this.mainMenuScene == null) {
            ProfilePageController controller = (ProfilePageController) this;
            FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass()
                    .getResource("BikeRentalApp.fxml"));
            try {
                Object root = fxmlLoader.load();
                BikeRentalAppController bikeRentalAppController = fxmlLoader.getController();
                if (loggedInUser != null) {
                    bikeRentalAppController.setLoggedInUser(loggedInUser);
                }
                if (root instanceof Parent) {
                    this.mainMenuScene = new Scene((Parent) root);
                } else if (root instanceof Scene) {
                    this.mainMenuScene = (Scene) root;
                }
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
        return this.mainMenuScene;
    }

    // TODO: Metode for å oppdatere utleiehistorikk (om vi velger å implementere
    // det):

    /*
     * private void updateRentalHistory() {
     * 
     * listOfRentalHistory.getItems().clear();
     * 
     * //TODO
     * for (String historyNode : history) {
     * 
     * listOfRentalHistory.getItems().add(historyNode);
     * }
     * 
     * }
     */

    /**
     * Shows error popup message in GUI.
     *
     * @param e errormessage to show in the error popup
     */
    private void errorMessage(String e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Feilmelding");
        alert.setContentText(e);
        alert.showAndWait();
    }

}