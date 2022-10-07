package bikeRentalApp.json.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import bikeRentalApp.core.User;
import bikeRentalApp.core.UserContainer;

public class UserContainerDeserializer extends JsonDeserializer<UserContainer> {

    private UserDeserializer userDeserializer = new UserDeserializer();

    @Override
    public UserContainer deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return this.deserialize((JsonNode) treeNode);
    }

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
