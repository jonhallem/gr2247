package bikerentalapp.json.internal;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import bikerentalapp.core.User;

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
    public void serialize(User user, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartObject();
        jGen.writeStringField("username", user.getUsername());
        jGen.writeStringField("password", user.getPassword());
        jGen.writePOJOField("bike", user.getBike());
        jGen.writeEndObject();
    }

}
