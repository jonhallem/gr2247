package bikerentalapp.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RemoteApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        RemoteApp app = (RemoteApp) this;
        FXMLLoader fxmlLoader = new FXMLLoader(app.getClass().getResource("BikeRentalRemoteApp.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
