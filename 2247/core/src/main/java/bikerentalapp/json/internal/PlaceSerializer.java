package bikerentalapp.json.internal;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * JSON serializer for the {@code Place} class.
 * Supports writing {@code Place} objects to JSON files through
 * {@code ObjectMapper} class.
 */
public class PlaceSerializer extends JsonSerializer<Place> {

    /*
     * JSON format:
     * {
     * "name": "...",
     * "maximumNumberOfBikes": x,
     * "bikes": "[ ... ]"
     * }
     */

    @Override
    public void serialize(Place place, JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", place.getName());
        jsonGen.writeStringField("maximumNumberOfBikes",
                String.valueOf(place.getMaximumNumberOfBikes()));
        jsonGen.writeArrayFieldStart("bikes");
        for (Bike bike : place) {
            jsonGen.writeObject(bike);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }
}
