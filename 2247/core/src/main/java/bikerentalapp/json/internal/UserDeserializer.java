package bikerentalapp.json.internal;

import bikerentalapp.core.Bike;
import bikerentalapp.core.User;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;

public class UserDeserializer extends JsonDeserializer<User> {

    private BikeDeserializer bikeDeserializer = new BikeDeserializer();

    @Override
    public User deserialize(JsonParser parser,
            DeserializationContext context) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return this.deserialize((JsonNode) treeNode);
    }

    public User deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode usernameNode = objectNode.get("username");
            JsonNode passwordNode = objectNode.get("password");
            JsonNode bikeNode = objectNode.get("bike");

            Bike bike = null;

            if (bikeNode.asText() != "null") {
                bike = bikeDeserializer.deserialize(bikeNode);
            }

            if (usernameNode instanceof TextNode && passwordNode instanceof TextNode) {
                return new User(((TextNode) usernameNode)
                        .asText(), ((TextNode) passwordNode).asText(), bike);
            }
        }
        return null;
    }
}
