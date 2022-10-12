package bikeRentalApp.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends ApplicationTest {
    /**
     * Rigorous Test :-)
     */

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("BikeRentalApp.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @BeforeEach
    public void setUpItems() {
        
    }


    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
