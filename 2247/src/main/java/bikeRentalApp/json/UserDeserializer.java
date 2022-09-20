package bikeRentalApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.mashape.unirest.http.JsonNode;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.User;

public class UserDeserializer extends JsonDeserializer<User>{

    private BikeDeserializer bikeDeserializer = new BikeDeserializer();

    @Override
    public User deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if(treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            JsonNode nameNode = objectNode.get("username");
            JsonNode passwordNode = objectNode.get("password");
            JsonNode bikeNode = objectNode.get("bike");
            
            // Bike newJsonNode = bikeNode.treeToValue(bikeNode, Bike.class)
        }

        return null;
        
        
    }
    
}
