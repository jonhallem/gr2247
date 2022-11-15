package bikerentalapp.json.internal;

import bikerentalapp.core.Bike;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;

public class BikeDeserializer extends JsonDeserializer<Bike> {

    @Override
    public Bike deserialize(JsonParser parser,
            DeserializationContext context) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return this.deserialize((JsonNode) treeNode);
    }

    public Bike deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode idNode = objectNode.get("iD");
            JsonNode typeNode = objectNode.get("type");
            JsonNode colourNode = objectNode.get("colour");
            if (idNode instanceof TextNode && typeNode instanceof TextNode
                    && colourNode instanceof TextNode) {
                return new Bike(((TextNode) idNode).asText(), ((TextNode) typeNode).asText(),
                        ((TextNode) colourNode).asText());
            }
        }
        return null;
    }

}
