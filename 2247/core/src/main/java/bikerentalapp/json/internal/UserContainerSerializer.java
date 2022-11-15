package bikerentalapp.json.internal;

import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class UserContainerSerializer extends JsonSerializer<UserContainer> {

    /*
     * JSON format:
     * {
     * "users": "[ ... ]"
     * }
     */

    @Override
    public void serialize(UserContainer userContainer,
            JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeArrayFieldStart("users");
        for (User user : userContainer) {
            jsonGen.writeObject(user);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }
}
