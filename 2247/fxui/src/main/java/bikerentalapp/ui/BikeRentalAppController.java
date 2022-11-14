package bikerentalapp.ui;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.User;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A class for the controller for the main functionality in Bike Rental App.
 * Defines functions that are called when interacting with the GUI
 * on the apps main pages, thus binding the GUI and the model.
 */
public class BikeRentalAppController {

    // -------------- Kobling til applikasjon -----------------

    private BikeRentalManagerAccess bikeRentalManagerAccess;

    private User loggedInUser = null;

    private Place chosenDepartureLocation;

    private Scene profilePageScene = null;

    // ------- URI for når RemoteApp kjøres -----

    @FXML
    String endpointUri;

    // -------------- Statisk innhold alltid synlig i applikasjonen
    // -----------------

    @FXML
    private Label appTitle;

    @FXML
    private Label yourBikeTitle;

    @FXML
    private Button profileButton;

    // -------------- Innhold i innlogging --------------------

    @FXML
    private Pane logInGroup;

    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button logInButton;

    @FXML
    private Button signUpButton;

    // -------------- Innhold i avreise ------------------------

    @FXML
    private Pane departureGroup;

    // TODO: Generer innhold med utgangspunkt i etablerte lokasjoner
    @FXML
    private ComboBox<String> selectDepartureLocation;

    @FXML
    private ListView<String> listOfAvailableBikes;

    @FXML
    private Button rentBikeButton;

    // -------------- Innhold i reise / adkomst -----------------

    @FXML
    private Pane arrivalGroup;

    @FXML
    private Button returnBikeButton;

    // -------------- Innhold i adkomst bekreftelse -----------------

    @FXML
    private Pane arrivalConfirmationGroup;

    @FXML
    private ComboBox<String> selectArrivalLocation;

    @FXML
    private Button confirmReturnBikeButton;

    // -------------- Innhold i "din sykkel" seksjon -----------------

    @FXML
    private Pane userInformationGroup;

    @FXML
    private Label rentedBikeIdText;

    // -------------- Metoder for riktig innlasting av GUI innhold -----------------

    /**
     * Initializes at the start of javafx instance, setting the initial values in
     * the app.
     */
    @FXML
    void initialize() {

        if (this.endpointUri == null) {
            this.bikeRentalManagerAccess = new DirectBikeRentalManagerAccess();
        } else {
            try {
                this.bikeRentalManagerAccess = new RemoteBikeRentalManagerAccess(new URI(endpointUri));
            } catch (URISyntaxException e) {
                System.err.println(e);
            }
        }

        rentedBikeIdText.setText("");

        logInGroup.setVisible(true);

        profileButton.setVisible(false);

        updateLocations();
    }

    /**
     * Sets the logged in user when the view is changed.
     *
     * @param loggedInUser the {@code User} object that is logged in during the
     *                     session.
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;

        logInGroup.setVisible(false);
        rentedBikeIdText.setText("");
        userInformationGroup.setVisible(true);
        logInGroup.setVisible(false);
        profileButton.setVisible(true);

        // sjekker om bruker allerede har lånt en sykkel
        if (this.loggedInUser.getBike() == null) {
            departureGroup.setVisible(true);
        } else {
            arrivalGroup.setVisible(true);
            rentedBikeIdText.setText(
                    this.loggedInUser.getBike().getType() + " - "
                            + this.loggedInUser.getBike().getID());
        }

    }

    public void setBikeRentalManagerAccess(BikeRentalManagerAccess bikeRentalManagerAccess) {
        if (bikeRentalManagerAccess instanceof RemoteBikeRentalManagerAccess) {
            RemoteBikeRentalManagerAccess remoteBikeRentalManagerAccess = (RemoteBikeRentalManagerAccess) bikeRentalManagerAccess;
            this.bikeRentalManagerAccess = new RemoteBikeRentalManagerAccess(remoteBikeRentalManagerAccess.getUri());
        } else {
            this.bikeRentalManagerAccess = new DirectBikeRentalManagerAccess();
        }
    }

    // -------- Brukerinnlogging og opprettelse ---------------

    /**
     * Logs into the application if the user exists in the database.
     * Catches IllegalArgumentException if a user does not exist in database.
     * Catches IOException when read/write to file has an unexpected error.
     * If an exception is catched, shows an appropriate error message.
     */
    @FXML
    private void logIn() {

        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            this.loggedInUser = bikeRentalManagerAccess.logIn(username, password);
            rentedBikeIdText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);
            profileButton.setVisible(true);

            // sjekker om bruker allerede har lånt en sykkel
            if (this.loggedInUser.getBike() == null) {
                departureGroup.setVisible(true);
            } else {
                arrivalGroup.setVisible(true);
                rentedBikeIdText.setText(
                        this.loggedInUser.getBike().getType() + " - "
                                + this.loggedInUser.getBike().getID());
            }
        } catch (IllegalArgumentException e) {

            errorMessage(e.toString());

        } catch (IOException e) {

            errorMessage(e.toString());
        }
    }

    /**
     * Creates a new user in the application and adds it to the database.
     * Catches IllegalArgumentException if a user exists in the database, or the
     * entered name/password is invalid.
     * Catches IOException when read/write to file has an unexpected error.
     * If an exception is catched, shows an appropriate error message.
     */
    @FXML
    private void signUp() {

        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            this.loggedInUser = bikeRentalManagerAccess.signUp(username, password);
            rentedBikeIdText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);
            departureGroup.setVisible(true);
            profileButton.setVisible(true);
        } catch (IllegalArgumentException e) {

            errorMessage(e.toString());

        } catch (IOException e) {

            errorMessage(e.toString());
        }
    }

    // ------------ Metoder for utlån og innlevering av sykler -----------

    /**
     * Rents a bike from the a place in the database and adds it the the user.
     * If the user has not selected a bike, show error message.
     * Catches IOException when read/write to file has an unexpected error.
     * If an exception is catched, shows an appropriate error message.
     */
    @FXML
    private void rentBike() {

        String selectedBike = listOfAvailableBikes.getSelectionModel().getSelectedItem();

        if (selectedBike != null) {

            String bikeId = selectedBike.split(" --- ")[0].split(": ")[1];

            for (Bike bike : chosenDepartureLocation.getBikes()) {
                if (bike.getID().equals(bikeId)) {
                    try {
                        this.loggedInUser = this.bikeRentalManagerAccess
                                .rentBike(chosenDepartureLocation.getName(), bike.getID(),
                                        this.loggedInUser.getUsername());
                    } catch (IOException e) {

                        errorMessage(e.toString());

                    } catch (IllegalArgumentException e) {
                        errorMessage(e.toString());
                    }
                    rentedBikeIdText.setText(bike.getType() + " - " + bike.getID());
                    break;
                }
            }

            departureGroup.setVisible(false);
            arrivalGroup.setVisible(true);

        } else {

            errorMessage("Velg en sykkel du ønsker å låne!");
        }
    }

    /**
     * Delivers bike object by removing it from the user in the database.
     * Catches Exception when read/write to file has an unexpected error, and shows
     * an appropriate error message.
     */
    @FXML
    private void deliverBike() {
        try {
            this.loggedInUser = this.bikeRentalManagerAccess.deliverBike(this.loggedInUser.getUsername(),
                    selectArrivalLocation.getValue());
            arrivalConfirmationGroup.setVisible(false);
            departureGroup.setVisible(true);
            rentedBikeIdText.setText("");
            loadBikesIntoList();

            // TODO: change exception?
        } catch (Exception e) {

            errorMessage(e.toString());
        }
    }

    /**
     * Shows returnGroup GUI-elements when user wants to deliver its bike.
     */
    @FXML
    private void showReturnGroup() {
        arrivalGroup.setVisible(false);
        arrivalConfirmationGroup.setVisible(true);
    }

    // ----------- Metode for å forandre viewet til profile page ----------------

    /**
     * Sets the view to the profile page.
     */
    @FXML
    void showProfilePage() {
        try {
            ((Stage) this.appTitle.getScene().getWindow()).setScene(getProfilePageScene());
        } catch (Exception e) {
            System.err.println("Couldn't load settings scene");
            e.getCause().printStackTrace();
        }
    }

    // ---------- Hjelpemetoder -------------

    /**
     * Refreshes the list of bikes available to rent.
     * Catches IOException when filehandling has an unexpected error, and shows the
     * error message.
     */
    @FXML
    private void loadBikesIntoList() {

        listOfAvailableBikes.getItems().clear();

        try {
            for (Place place : bikeRentalManagerAccess.getPlaceContainer().getPlaces()) {
                if (place.getName().equals(selectDepartureLocation.getValue())) {
                    chosenDepartureLocation = place;
                }
            }
        } catch (IOException e) {

            errorMessage(e.toString());
        }

        for (Bike bike : chosenDepartureLocation) {

            StringBuilder sb = new StringBuilder();
            sb.append("SykkelID: ");
            sb.append(bike.getID());
            sb.append(" --- ");
            sb.append("Type: ");
            sb.append(bike.getType());
            sb.append(" --- ");
            sb.append("Farge: ");
            sb.append(bike.getColour());

            listOfAvailableBikes.getItems().add(sb.toString());
        }
    }

    /**
     * Shows error popup message in GUI.
     *
     * @param e errormessage in the error popup
     */
    private void errorMessage(String e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Feilmelding");
        alert.setContentText(e);
        alert.showAndWait();
    }

    /**
     * Updates places in GUI comboboxes.
     * Catches IOException when read/write to file has an unexpected error, and
     * shows an error message.
     */
    private void updateLocations() {

        try {
            for (Place place : bikeRentalManagerAccess.getPlaceContainer().getPlaces()) {
                selectDepartureLocation.getItems().add(place.getName());
                selectArrivalLocation.getItems().add(place.getName());
            }
        } catch (IOException e) {

            errorMessage(e.toString());
        }

    }

    /**
     * Creates and returns the profile page scene, used to set the view. Transfers
     * the user object to the controller of the profile page (ensures that the user
     * is still logged in).
     *
     * @return Scene the {@code Scene} object to set the view to.
     * @throws RuntimeException if an IOExceprion happens when the fxmlLoader is
     *                          loaded.
     */
    private Scene getProfilePageScene() throws RuntimeException {
        if (this.profilePageScene == null) {
            BikeRentalAppController controller = (BikeRentalAppController) this;
            FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass()
                    .getResource("BikeRentalProfilePage.fxml"));
            try {
                Object root = fxmlLoader.load();
                ProfilePageController profilePageController = fxmlLoader.getController();
                profilePageController.setLoggedInUser(this.loggedInUser);
                profilePageController.setBikeRentalManagerAccess(this.bikeRentalManagerAccess);
                if (root instanceof Parent) {
                    this.profilePageScene = new Scene((Parent) root);
                } else if (root instanceof Scene) {
                    this.profilePageScene = (Scene) root;
                }
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
        return this.profilePageScene;
    }

    // ---------- Metoder for testing -------------

    /**
     * Returns the logged in user.
     *
     * @return the {@code User} object logged in.
     */
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * Returns the {@code Bike} object that the logged in user has rented.
     *
     * @return {@code null} if no bike is rented, otherwise the {@code Bike} object.
     */
    public Bike getUserBike() {
        return this.loggedInUser.getBike();
    }

}
