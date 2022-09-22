package bikeRentalApp.core;

import java.io.FileNotFoundException;

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
    

    private BikeRentalDataHandler dataHandler;



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


    @FXML
    void initialize() throws FileNotFoundException {

        bikeRentalManager = new BikeRentalManager();

        bikeRentalManager.testMethod();
        rentedBikeIDText.setText("");
        //dataHandler.readUsers();

        logInGroup.setVisible(true);

        updateLocations();
    }


    // -------- Brukerinnlogging og opprettelse ---------------
    @FXML
    private void logIn() {

        //TODO: connection login to db

    String username = usernameInput.getText();
    String password = passwordInput.getText();

        try {
            bikeRentalManager.logIn(username, password);
            rentedBikeIDText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);
            departureGroup.setVisible(true);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    @FXML
    private void signUp() {

        //TODO: connection login to db

    String username = usernameInput.getText();
    String password = passwordInput.getText();

        try {
            bikeRentalManager.signUp(username, password);
            rentedBikeIDText.setText("");
            userInformationGroup.setVisible(true);
            logInGroup.setVisible(false);
            departureGroup.setVisible(true);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    // ------------ Metoder for utlån og innlevering av sykler -----------

    // utlån av sykkel
    @FXML
    private void rentBike() {
        
        String selectedBike = listOfAvailableBikes.getSelectionModel().getSelectedItem();

        if (selectedBike != null) {
            
            String bikeID = selectedBike.split(" --- ")[0].split(": ")[1];

            for (Bike bike : chosenDepartureLocation.getBikes()) {
                if (bike.getID().equals(bikeID)) {
                    bikeRentalManager.rentBike(selectDepartureLocation.getValue(), bike.getID());
                    rentedBikeIDText.setText(bike.getType() + " - " + bike.getID());
                }
            }

            departureGroup.setVisible(false);
            arrivalGroup.setVisible(true);

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setContentText("Velg en sykkel du ønsker å låne!");
            alert.showAndWait();
        }
    }

    // innlevering av sykkel
    @FXML
    private void deliverBike() {
        try {
            bikeRentalManager.deliverBike(selectArrivalLocation.getValue());
            arrivalConfirmationGroup.setVisible(false);
            departureGroup.setVisible(true);
            rentedBikeIDText.setText("");
            loadBikesIntoList();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Feilmelding");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    // Visning av bekreftelses-vindu for innlevering
    @FXML
    private void showReturnGroup() {
        arrivalGroup.setVisible(false);
        arrivalConfirmationGroup.setVisible(true);
    }




    // ---------- Hjelpemetoder -------------

    
    @FXML
    private void loadBikesIntoList() {
        
        listOfAvailableBikes.getItems().clear();

        for (Place place : bikeRentalManager.getPlaces()) {
            if (place.getName().equals(selectDepartureLocation.getValue())) {
                chosenDepartureLocation = place;
            }
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

        // TODO: bruke denne i stedet?
        // bikeRentalManager.getBikeInPlace(chosenDepartureLocation);
    }


    // legger inn lokasjoner i comboboxer
    private void updateLocations() {

        for (Place place : bikeRentalManager.getPlaces()) {
            selectDepartureLocation.getItems().add(place.getName());
            selectArrivalLocation.getItems().add(place.getName());
        }

    }

}
