package bikerentalapp.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A class that launches the Bike Rental app with remote model access.
 */
public class RemoteApp extends Application {

    /**
     * Launches the Bike Rental app with remote model access.
     *
     * @param stage the stage to launch.
     */
    @Override
    public void start(Stage stage) throws IOException {
        RemoteApp app = (RemoteApp) this;
        FXMLLoader fxmlLoader = new FXMLLoader(app.getClass()
                .getResource("BikeRentalRemoteApp.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
