package bikerentalapp.json.internal;

import bikerentalapp.core.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {

    /*
     * JSON format:
     * {
     * "username": "...",
     * "password": "...",
     * "bike": [...]
     * }
     */

    @Override
    public void serialize(User user, JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("username", user.getUsername());
        jsonGen.writeStringField("password", user.getPassword());
        jsonGen.writePOJOField("bike", user.getBike());
        jsonGen.writeEndObject();
    }

}
