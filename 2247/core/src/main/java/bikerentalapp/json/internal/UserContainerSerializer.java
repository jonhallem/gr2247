package bikerentalapp.json.internal;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;

public class UserContainerSerializer extends JsonSerializer<UserContainer> {

    /*
     * JSON format:
     * {
     * "users": "[ ... ]"
     * }
     */

    @Override
    public void serialize(UserContainer userContainer, JsonGenerator jGen, SerializerProvider serializerProvider)
            throws IOException {
        jGen.writeStartObject();
        jGen.writeArrayFieldStart("users");
        for (User user : userContainer) {
            jGen.writeObject(user);
        }
        jGen.writeEndArray();
        jGen.writeEndObject();
    }
}
