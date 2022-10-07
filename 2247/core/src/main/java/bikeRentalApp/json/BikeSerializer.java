package bikeRentalApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import bikeRentalApp.core.Bike;

public class BikeSerializer extends JsonSerializer<Bike> {

    /*
    JSON format:
        {
            "iD": "...",
            "type": "...",
            "colour": "..."
        }
     */

    @Override
    public void serialize(Bike bike, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartObject();
        jGen.writeStringField("iD", bike.getID());
        jGen.writeStringField("type", bike.getType());
        jGen.writeStringField("colour", bike.getColour());
        jGen.writeEndObject();
    }
}
