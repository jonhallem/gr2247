package bikeRentalApp.json;

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
import com.fasterxml.jackson.databind.node.TextNode;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.Place;

public class PlaceDeserializer extends JsonDeserializer<Place> {

    private BikeDeserializer bikeDeserializer = new BikeDeserializer();

    @Override
    public Place deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if (treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            JsonNode nameNode = objectNode.get("name");
            JsonNode maximumNumberOfBikesNode = objectNode.get("maximumNumberOfBikes");
            List<Bike> bikes = new ArrayList<>();
            JsonNode bikesNode = objectNode.get("bikes");
            if (bikesNode instanceof ArrayNode) {
                for (JsonNode bikeNode : ((ArrayNode) bikesNode)) {
                    Bike bike = bikeDeserializer.deserialize(bikeNode);
                    if (bike != null) {
                        bikes.add(bike);
                    }
                }
            }
            if (nameNode instanceof TextNode && maximumNumberOfBikesNode instanceof TextNode) {
                return new Place(((TextNode) nameNode).asText(), ((TextNode) maximumNumberOfBikesNode).asInt(), bikes);
            }
        }
        return null;
    }
}
