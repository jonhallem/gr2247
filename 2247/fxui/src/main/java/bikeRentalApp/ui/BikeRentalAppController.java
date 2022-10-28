package bikeRentalApp.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import bikeRentalApp.core.BikeRentalManager;
import bikeRentalApp.core.Place;
import bikeRentalApp.core.User;
import bikeRentalApp.core.Bike;


public class BikeRentalAppController {
    


    // -------------- Kobling til applikasjon -----------------

    private BikeRentalManager bikeRentalManager;

    private Place chosenDepartureLocation;

    private Scene profilePageScene = null;
    



    // -------------- Statisk innhold alltid synlig i applikasjonen -----------------

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

        profileButton.setVisible(false);

        updateLocations();
    }

    /**
     * Sets the bikeRentalManager when the view is changed
     */
    public void setBikeRentalManager(BikeRentalManager BRM) {
        this.bikeRentalManager = BRM;

        logInGroup.setVisible(false);
        rentedBikeIDText.setText("");
        userInformationGroup.setVisible(true);
        logInGroup.setVisible(false);
        profileButton.setVisible(true);

        // sjekker om bruker allerede har lånt en sykkel
        if (bikeRentalManager.getUserBike() == null) {
            departureGroup.setVisible(true);
        } else {
            arrivalGroup.setVisible(true);
            rentedBikeIDText.setText(bikeRentalManager.getUserBike().getType() + " - " + bikeRentalManager.getUserBike().getID());
        }

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
            profileButton.setVisible(true);

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
            profileButton.setVisible(true);
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



    // ----------- Metode for å forandre viewet til profile page ----------------
      

      /**
     * Sets the view to profile page.
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


    /**
     * Creates and returns the profile page scene, used to set the view. Transfers the bikeRentalManager
     * object to the controller of the profile page (ensures that the user is still logged in).
     * 
     * @return Scene the {@code Scene} object to set the view to.
     * @throws RuntimeException
     */
    private Scene getProfilePageScene() throws RuntimeException {
        if (this.profilePageScene == null) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BikeRentalProfilePage.fxml"));
        try {
            Object root = fxmlLoader.load();
            ProfilePageController profilePageController = fxmlLoader.getController();
            profilePageController.setBikeRentalManager(this.bikeRentalManager);
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
     * Returns logged in user
     */
    public User getLoggedInUser() {
        return bikeRentalManager.getLoggedInUser();
    }

        /**
     * Returns rented bike
     */
    public Bike getUserBike() {
        return bikeRentalManager.getUserBike();
    }


}
