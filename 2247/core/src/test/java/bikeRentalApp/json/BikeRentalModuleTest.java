package bikeRentalApp.json;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.Place;
import bikeRentalApp.core.PlaceContainer;
import bikeRentalApp.core.User;
import bikeRentalApp.core.UserContainer;
import bikeRentalApp.json.internal.BikeRentalModule;

public class BikeRentalModuleTest {
    
    private static ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testSerializers_PlaceContainer() {
        mapper.registerModule(new BikeRentalModule());
        
        Place place1 = new Place("Munkholmen", 10);
        Place place2 = new Place("Gløshaugen", 20);
        Bike bike1 =  new Bike("BIKE1234", "Terrengsykkel", "Blå");
        Bike bike2 =  new Bike("BIKE9876", "Tandemsykkel", "Svart");
        Bike bike3 =  new Bike("BIKEBIKE", "Fjellsykkel", "Lilla");

        place1.addBike(bike1);
        place2.addBike(bike2);
        place2.addBike(bike3);

        List<Place> listOfPlaces = new ArrayList<>();
        listOfPlaces.add(place1);
        listOfPlaces.add(place2);

        PlaceContainer placeContainer = new PlaceContainer(listOfPlaces);

        try {
            assertEquals(
                "{\"places\":[{\"name\":\"Munkholmen\",\"maximumNumberOfBikes\":\"10\",\"bikes\":[{\"iD\":\"" +
                "BIKE1234\",\"type\":\"Terrengsykkel\",\"colour\":\"Blå\"}]},{\"name\":\"Gløshaugen\",\"maximumNumberOfBikes\"" +
                ":\"20\",\"bikes\":[{\"iD\":\"BIKE9876\",\"type\":\"Tandemsykkel\",\"colour\":\"Svart\"},{\"iD\":\"BIKEBIKE\",\"" +
                "type\":\"Fjellsykkel\",\"colour\":\"Lilla\"}]}]}", 
            mapper.writeValueAsString(placeContainer));
        } catch (JsonProcessingException e) {
            fail();
        }

        
    }

    @Test
    public void testSerializers_UserContainer() {
        mapper.registerModule(new BikeRentalModule());

        User user1 = new User("Jarl", "jarl123");
        User user2 = new User("Jon", "jon123");
        User user3 = new User("Mikkel", "mikkel123");

        Bike bike1 =  new Bike("BIKE1234", "Terrengsykkel", "Blå");

        user2.setBike(bike1);

        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(user1);
        listOfUsers.add(user2);
        listOfUsers.add(user3);

        UserContainer userContainer = new UserContainer(listOfUsers);

        try {
            assertEquals(
                "{\"users\":[{\"username\":\"Jarl\",\"password\":\"jarl123\",\"bike\":null}," + 
                "{\"username\":\"Jon\",\"password\":\"jon123\",\"bike\":{\"iD\":\"BIKE1234\"," + 
                "\"type\":\"Terrengsykkel\",\"colour\":\"Blå\"}}," +
                "{\"username\":\"Mikkel\",\"password\":\"mikkel123\",\"bike\":null}]}", 
                mapper.writeValueAsString(userContainer));
        } catch (JsonProcessingException e) {
            fail();
        }
    }

}
