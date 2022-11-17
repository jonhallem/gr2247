module bikerentalapp.ui {
    requires com.fasterxml.jackson.databind;

    requires java.net.http;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires bikerentalapp.core;

    opens bikerentalapp.ui to javafx.graphics, javafx.fxml;

}
