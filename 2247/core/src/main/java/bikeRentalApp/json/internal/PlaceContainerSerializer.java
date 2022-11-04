package bikerentalapp.json.internal;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;

public class PlaceContainerSerializer extends JsonSerializer<PlaceContainer> {

    /*
    JSON format:
        {
            "places": "[ ... ]"
        }
     */

    @Override
    public void serialize(PlaceContainer placeContainer, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartObject();
        jGen.writeArrayFieldStart("places");
        for (Place place : placeContainer) {
            jGen.writeObject(place);
        }
        jGen.writeEndArray();
        jGen.writeEndObject();
    }
}
