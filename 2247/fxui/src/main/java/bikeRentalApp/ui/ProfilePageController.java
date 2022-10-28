package bikeRentalApp.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

import bikeRentalApp.core.BikeRentalManager;


public class ProfilePageController {



    private BikeRentalManager bikeRentalManager;



    // -------------- FXML-elementer i header -----------------

    @FXML
    private Button backToMainMenuButton, logOutButton;

    @FXML
    private Label usernameTitle;


    // -------------- FXML-elementer relatert til endring av passord -----------------

    @FXML
    private Button changePasswordButton, abortChangePasswordButton, confirmNewPasswordButton;

    @FXML
    private Pane changePasswordPane;

    @FXML
    private TextField currentPasswordInput, newPasswordInput, repeatNewPasswordInput;
    


    // -------------- FXML-elementer relatert til leiehistorikk av passord -----------------

    @FXML
    private ListView<String> listOfRentalHistory;




    // ----------- Initialisering -------------


    @FXML
    void initialize() {

        // denne må være med
        // bikeRentalManager = new BikeRentalManager();

        //updateRentalHistory();
        this.updateUserName();
    }


    

    // ----------- Metoder for endring av passord -------------

    @FXML
    private void showChangePasswordPane() {
        this.changePasswordPane.setVisible(true);
    }

    @FXML 
    private void hideChangePasswordPane() {
        this.changePasswordPane.setVisible(false);
    }

    @FXML
    private void confirmNewPassword() {
        String currentPassword = this.currentPasswordInput.getText();
        String newPassword = this.newPasswordInput.getText();
        String newPasswordRepeated = this.repeatNewPasswordInput.getText();

        if (!currentPassword.equals(this.bikeRentalManager.getLoggedInUser().getPassword())) {
            this.errorMessage("Passordene i feltet \"Nytt passord\" og feltet \"Gjenta nytt passord\" stemmer ikke overens");

        }
        if (!newPassword.equals(newPasswordRepeated)) {
            this.errorMessage("Passordene i feltet \"Nytt passord\" og feltet \"Gjenta nytt passord\" stemmer ikke overens");
        }
        try {
            this.bikeRentalManager.getLoggedInUser().changePassword(newPassword);
        } catch (IllegalArgumentException e) {
            this.errorMessage(e.getMessage());
        }
    }



    // ----------- Metode for å logge ut ----------------

    @FXML
    private void logOut() {
        this.bikeRentalManager.logOut();

        //TODO: Endre view til log-in side
    }


    // ----------- Metode for å gå tilbake til hovedvinduet ----------------

    @FXML 
    private void backToMainMenu() {

        //TODO: Endre view til main menu
    }



    // ----------- Hjelpe metoder ----------------

    private void updateUserName() {
        this.usernameTitle.setText("Bruker: " + this.bikeRentalManager.getLoggedInUser().getUsername());
    }



     /**
     * Refreshes the list of bikes available to rent.
     * Catches IOException when filehandling has an unexpected error
     */
    /* private void updateRentalHistory() {

        listOfRentalHistory.getItems().clear();

        //TODO
        for (String historyNode : history) {
            
            listOfRentalHistory.getItems().add(historyNode);
        }
        
    } */


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

}