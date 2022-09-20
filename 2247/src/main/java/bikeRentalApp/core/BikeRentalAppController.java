package bikeRentalApp.core;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class BikeRentalAppController {
    


    // -------------- Kobling til applikasjon -----------------

    private BikeRentalManager bikeRentalManager;





    // -------------- Statisk innhold alltid synlig i applikasjonen -----------------

    @FXML
    private Label appTitle;

    @FXML
    private Label yourBikeTitle;






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

    //TODO: Generer innhold med utgangspunkt i etablerte lokasjoner
    @FXML
    private ComboBox selectDepartureLocation;

    @FXML
    private ListView listOfAvailableBikes;





    // -------------- Innhold i reise / adkomst -----------------

    @FXML
    private Pane arrivalGroup;

    @FXML
    private Button returnBikeButton;





    // -------------- Innhold i adkomst bekreftelse -----------------

    @FXML
    private Pane arrivalConfirmationGroup;

    @FXML
    private ComboBox selectArrivalLocation;

    @FXML
    private Button confirmReturnBikeButton;





    // -------------- Innhold i "din sykkel" seksjon -----------------

    @FXML
    private Pane userInformationGroup;

    @FXML
    private Label rentedBikeIDText;





    // -------------- Metoder for riktig innlasting av innhold -----------------


    @FXML
    void initialize() {
        logInGroup.setVisible(true);
    }





    private void logIn() {

        //TODO: connection login to db

    String username = usernameInput.getText();
    String password = passwordInput.getText();

        try {
            bikeRentalManager.logIn(username, password);
            rentedBikeIDText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);
        } catch (IllegalArgumentException e) {
            // TODO: handle exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setContentText(e.toString());
        }
    }





}
