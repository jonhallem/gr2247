package bikerentalapp.json.internal;

import bikerentalapp.core.Bike;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class BikeSerializer extends JsonSerializer<Bike> {

    /*
     * JSON format:
     * {
     * "iD": "...",
     * "type": "...",
     * "colour": "..."
     * }
     */

    @Override
    public void serialize(Bike bike, JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("iD", bike.getId());
        jsonGen.writeStringField("type", bike.getType());
        jsonGen.writeStringField("colour", bike.getColour());
        jsonGen.writeEndObject();
    }
}
