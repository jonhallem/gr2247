package bikeRentalApp.core;

import java.io.IOException;

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

    private Place chosenDepartureLocation;
    



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
    private Label rentedBikeIDText;





    // -------------- Metoder for riktig innlasting av GUI innhold -----------------


    /**
     * Initializes at the start of javafx instance
     */
    @FXML
    void initialize() {

        bikeRentalManager = new BikeRentalManager();

        rentedBikeIDText.setText("");

        logInGroup.setVisible(true);

        updateLocations();
    }


    // -------- Brukerinnlogging og opprettelse ---------------

    /**
     * Logs into the application if the user exists in the database.
     * Catches IllegalArgumentException if a user does not exist in database.
     * Catches IOException when filehandling has an unexpected error
     */
    @FXML
    private void logIn() {

    String username = usernameInput.getText();
    String password = passwordInput.getText();

        try {
            bikeRentalManager.logIn(username, password);
            rentedBikeIDText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);

            // sjekker om bruker allerede har lånt en sykkel
            if (bikeRentalManager.getUserBike() == null) {
                departureGroup.setVisible(true);
            } else {
                arrivalGroup.setVisible(true);
                rentedBikeIDText.setText(bikeRentalManager.getUserBike().getType() + " - " + bikeRentalManager.getUserBike().getID());
            }
        } catch (IllegalArgumentException e) {

            errorMessage(e.toString());

        } catch (IOException e) {

            errorMessage(e.toString());
        }
    }

    /**
     * Create user in the application and add it to the database.
     * Catches IllegalArgumentException if a user exists in the database, or the entered name/password is invalid.
     * Catches IOException when filehandling has an unexpected error
     */
    @FXML
    private void signUp() {

    String username = usernameInput.getText();
    String password = passwordInput.getText();

        try {
            bikeRentalManager.signUp(username, password);
            rentedBikeIDText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);
            departureGroup.setVisible(true);
        } catch (IllegalArgumentException e) {

            errorMessage(e.toString());

        } catch (IOException e) {

            errorMessage(e.toString());
        }
    }

    // ------------ Metoder for utlån og innlevering av sykler -----------


    /**
     * Rents a bike from the database and add it the the user.
     * If the user has not selected a bike, show error message.
     * Catches IOException when filehandling has an unexpected error
     */
    @FXML
    private void rentBike() {
        
        String selectedBike = listOfAvailableBikes.getSelectionModel().getSelectedItem();

        if (selectedBike != null) {
            
            String bikeID = selectedBike.split(" --- ")[0].split(": ")[1];

            for (Bike bike : chosenDepartureLocation.getBikes()) {
                if (bike.getID().equals(bikeID)) {
                    try {
                        bikeRentalManager.rentBike(selectDepartureLocation.getValue(), bike.getID());
                    } catch (IOException e) {
                        
                        errorMessage(e.toString());

                    } catch(IllegalArgumentException e) {
                        errorMessage(e.toString());
                    }
                    rentedBikeIDText.setText(bike.getType() + " - " + bike.getID());
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
     * Catches Exception when filehandling has an unexpected error
     */
    @FXML
    private void deliverBike() {
        try {
            bikeRentalManager.deliverBike(selectArrivalLocation.getValue());
            arrivalConfirmationGroup.setVisible(false);
            departureGroup.setVisible(true);
            rentedBikeIDText.setText("");
            loadBikesIntoList();

            //TODO: change exception?
        } catch (Exception e) {

            errorMessage(e.toString());
        }
    }

     /**
     * Shows returnGroup GUI-elements when user wants to deliver bike.
     */
    @FXML
    private void showReturnGroup() {
        arrivalGroup.setVisible(false);
        arrivalConfirmationGroup.setVisible(true);
    }




    // ---------- Hjelpemetoder -------------


     /**
     * Refreshes the list of bikes available to rent.
     * Catches IOException when filehandling has an unexpected error
     */
    @FXML
    private void loadBikesIntoList() {
        
        listOfAvailableBikes.getItems().clear();

        try {
            for (Place place : bikeRentalManager.getPlaces()) {
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
     * Catches IOException when filehandling has an unexpected error
     */
    private void updateLocations() {

        try {
            for (Place place : bikeRentalManager.getPlaces()) {
                selectDepartureLocation.getItems().add(place.getName());
                selectArrivalLocation.getItems().add(place.getName());
            }
        } catch (IOException e) {
            
            errorMessage(e.toString());
        }

    }

}
