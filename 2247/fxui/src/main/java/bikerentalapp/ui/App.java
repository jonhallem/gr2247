package bikerentalapp.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A class that launches the Bike Rental app.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        App app = (App) this;
        FXMLLoader fxmlLoader = new FXMLLoader(app.getClass().getResource("BikeRentalApp.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}