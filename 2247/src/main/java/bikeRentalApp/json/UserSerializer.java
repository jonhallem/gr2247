package bikeRentalApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import bikeRentalApp.core.User;

public class UserSerializer extends JsonSerializer<User>{

    /*
    JSON format: 
        {
            "username": "...",
            "password": "...",
            "bike": "..."
        }

     */

    @Override
    public void serialize(User arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        // TODO Auto-generated method stub
        
    }
    
}
