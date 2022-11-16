package bikerentalapp.json.internal;

import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
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
 * JSON deserializer for the {@code PlaceContainer} class.
 * Supports reading and instantiating {@code PlaceContainer} objects from JSON
 * files through {@code ObjectMapper} class.
 */
public class PlaceContainerDeserializer extends JsonDeserializer<PlaceContainer> {

    private PlaceDeserializer placeDeserializer = new PlaceDeserializer();

    @Override
    public PlaceContainer deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return this.deserialize((JsonNode) treeNode);
    }

    /**
     * Deserializes a {@code JsonNode} object to instantiate a new
     * {@code PlaceContainer}
     * object with the correct properties.
     *
     * @param jsonNode to deserialize.
     * @return a instantiatied {@code PlaceContainer} object with properties
     *         according to the
     *         JsonNode.
     */
    public PlaceContainer deserialize(JsonNode jsonNode) {
        List<Place> placeList = new ArrayList<>();
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode placesNode = objectNode.get("places");
            if (placesNode instanceof ArrayNode) {
                for (JsonNode placeNode : ((ArrayNode) placesNode)) {
                    Place place = placeDeserializer.deserialize(placeNode);
                    if (place != null) {
                        placeList.add(place);
                    }
                }
            }
        }
        return new PlaceContainer(placeList);
    }

}
