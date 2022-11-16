package bikerentalapp.json;

import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import bikerentalapp.core.PlaceContainer;

public class DefaultPlaceContainerTest {

    private ObjectMapper mapper = BikeRentalPersistence.getObjectMapper();

    @DisplayName("Tester at DefualtPlaceContainerString har riktig format")
    @Test
    public void testGetDefaultPlaceContainerString() {
        new DefaultPlaceContainer();

        String defaultPlaceContainerString = DefaultPlaceContainer.getDefaultPlaceContainerString();

        try {
            mapper.readValue(defaultPlaceContainerString,
                    PlaceContainer.class);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}
