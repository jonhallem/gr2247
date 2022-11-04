package bikerentalapp.json.internal;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import bikerentalapp.core.Bike;

public class BikeDeserializer extends JsonDeserializer<Bike> {

    @Override
    public Bike deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return this.deserialize((JsonNode) treeNode);
    }

    public Bike deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode iDNode = objectNode.get("iD");
            JsonNode typeNode = objectNode.get("type");
            JsonNode colourNode = objectNode.get("colour");
            if (iDNode instanceof TextNode && typeNode instanceof TextNode && colourNode instanceof TextNode) {
                return new Bike(((TextNode) iDNode).asText(), ((TextNode) typeNode).asText(),
                        ((TextNode) colourNode).asText());
            }
        }
        return null;
    }

}
