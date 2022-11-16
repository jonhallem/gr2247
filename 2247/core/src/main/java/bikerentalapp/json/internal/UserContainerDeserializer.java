package bikerentalapp.json.internal;

import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON deserializer for the {@code UserContainer} class.
 * Supports reading and instantiating {@code UserContainer} objects from JSON
 * files through {@code ObjectMapper} class.
 */
public class UserContainerDeserializer extends JsonDeserializer<UserContainer> {

    private UserDeserializer userDeserializer = new UserDeserializer();

    @Override
    public UserContainer deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return this.deserialize((JsonNode) treeNode);
    }

    /**
     * Deserializes a {@code JsonNode} object to instantiate a new
     * {@code UserContainer}
     * object with the correct properties.
     *
     * @param jsonNode to deserialize.
     * @return a instantiatied {@code UserContainer} object with properties
     *         according to the
     *         JsonNode.
     */
    public UserContainer deserialize(JsonNode jsonNode) {
        List<User> userList = new ArrayList<>();
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode usersNode = objectNode.get("users");
            if (usersNode instanceof ArrayNode) {
                for (JsonNode userNode : ((ArrayNode) usersNode)) {
                    User user = userDeserializer.deserialize(userNode);
                    if (user != null) {
                        userList.add(user);
                    }
                }
            }
        }
        return new UserContainer(userList);
    }

}
