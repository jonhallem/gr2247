package bikeRentalApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.Place;

public class PlaceSerializer extends JsonSerializer<Place> {

    /*
    JSON format:
        {
            "name": "...",
            "maximumNumberOfBikes": x,
            "bikes": "[ ... ]"
        }
     */

    @Override
    public void serialize(Place place, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartObject();
        jGen.writeStringField("name", place.getName());
        jGen.writeStringField("maximumNumberOfBikes", String.valueOf(place.getMaximumNumberOfBikes()));
        jGen.writeArrayFieldStart("bikes");
        for (Bike bike : place) {
            jGen.writeObject(bike);
        }
        jGen.writeEndArray();
        jGen.writeEndObject();
    }
}
