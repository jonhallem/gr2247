package bikerentalapp.json.internal;

import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class PlaceContainerSerializer extends JsonSerializer<PlaceContainer> {

    /*
     * JSON format:
     * {
     * "places": "[ ... ]"
     * }
     */

    @Override
    public void serialize(PlaceContainer placeContainer,
            JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeArrayFieldStart("places");
        for (Place place : placeContainer) {
            jsonGen.writeObject(place);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }
}
