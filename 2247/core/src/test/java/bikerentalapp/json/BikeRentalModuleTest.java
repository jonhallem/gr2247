package bikerentalapp.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;
import bikerentalapp.json.internal.BikeRentalModule;

public class BikeRentalModuleTest {

    private static ObjectMapper mapper = new ObjectMapper();

    final static String placeContainerAsJSON = """
                {
                    "places" : [ {
                      "name" : "Bymarka",
                      "maximumNumberOfBikes" : "5",
                      "bikes" : [ {
                        "iD" : "BIKEIDN1",
                        "type" : "Tandemsykkel",
                        "colour" : "Gul"
                      }, {
                        "iD" : "BIKEIDN2",
                        "type" : "Terrengsykkel",
                        "colour" : "Blå"
                      } ]
                    }, {
                      "name" : "Munkholmen",
                      "maximumNumberOfBikes" : "10",
                      "bikes" : [ {
                        "iD" : "BIKEIDN3",
                        "type" : "Tandemsykkel",
                        "colour" : "Grønn"
                      } ]
                    }, {
                      "name" : "Lerkendal",
                      "maximumNumberOfBikes" : "10",
                      "bikes" : [ ]
                    } ]
                  }
            """;

    final static String userContainerAsJSON = """
                {
                    "users" : [ {
                      "username" : "Jarl",
                      "password" : "jarl123",
                      "bike" : null
                    }, {
                      "username" : "Jon",
                      "password" : "jon123",
                      "bike" : null
                    }, {
                      "username" : "Mikkel",
                      "password" : "mikkel123",
                      "bike" : {
                        "iD" : "BIKEIDN2",
                        "type" : "Tandemsykkel",
                        "colour" : "Gul"
                      }
                    } ]
                  }
            """;

    @Test
    public void testSerializers_PlaceContainer() {
        mapper.registerModule(new BikeRentalModule());

        Place place1 = new Place("Munkholmen", 10);
        Place place2 = new Place("Gløshaugen", 20);
        Bike bike1 = new Bike("BIKE1234", "Terrengsykkel", "Blå");
        Bike bike2 = new Bike("BIKE9876", "Tandemsykkel", "Svart");
        Bike bike3 = new Bike("BIKEBIKE", "Fjellsykkel", "Lilla");

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
                            "BIKE1234\",\"type\":\"Terrengsykkel\",\"colour\":\"Blå\"}]},{\"name\":\"Gløshaugen\",\"maximumNumberOfBikes\""
                            +
                            ":\"20\",\"bikes\":[{\"iD\":\"BIKE9876\",\"type\":\"Tandemsykkel\",\"colour\":\"Svart\"},{\"iD\":\"BIKEBIKE\",\""
                            +
                            "type\":\"Fjellsykkel\",\"colour\":\"Lilla\"}]}]}",
                    mapper.writeValueAsString(placeContainer));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testSerializers_UserContainer() {
        mapper.registerModule(new BikeRentalModule());

        User user1 = new User("Jarl", "jarl123");
        User user2 = new User("Jon", "jon123");
        User user3 = new User("Mikkel", "mikkel123");

        Bike bike1 = new Bike("BIKE1234", "Terrengsykkel", "Blå");

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
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeserializers_PlaceContainer() {
        mapper.registerModule(new BikeRentalModule());
        try {
            PlaceContainer placeContainer = mapper.readValue(placeContainerAsJSON, PlaceContainer.class);
            Place place1 = placeContainer.getPlaces().get(0);
            Bike bike1AtPlace1 = place1.getBikes().get(0);
            Bike bike2AtPlace1 = place1.getBikes().get(1);

            Place place2 = placeContainer.getPlaces().get(1);
            Bike bike1AtPlace2 = place2.getBikes().get(0);

            Place place3 = placeContainer.getPlaces().get(2);

            assertEquals("Bymarka", place1.getName());
            assertEquals(5, place1.getMaximumNumberOfBikes());
            assertEquals(2, place1.getBikes().size());
            assertEquals("BIKEIDN1", bike1AtPlace1.getID());
            assertEquals("Tandemsykkel", bike1AtPlace1.getType());
            assertEquals("Gul", bike1AtPlace1.getColour());
            assertEquals("BIKEIDN2", bike2AtPlace1.getID());
            assertEquals("Terrengsykkel", bike2AtPlace1.getType());
            assertEquals("Blå", bike2AtPlace1.getColour());

            assertEquals("Munkholmen", place2.getName());
            assertEquals(10, place2.getMaximumNumberOfBikes());
            assertEquals(1, place2.getBikes().size());
            assertEquals("BIKEIDN3", bike1AtPlace2.getID());
            assertEquals("Tandemsykkel", bike1AtPlace2.getType());
            assertEquals("Grønn", bike1AtPlace2.getColour());

            assertEquals("Lerkendal", place3.getName());
            assertEquals(10, place3.getMaximumNumberOfBikes());
            assertEquals(0, place3.getBikes().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeserializers_UserContainer() {
        mapper.registerModule(new BikeRentalModule());

        try {
            UserContainer userContainer = mapper.readValue(userContainerAsJSON, UserContainer.class);
            User user1 = userContainer.getUsers().get(0);
            User user2 = userContainer.getUsers().get(1);
            User user3 = userContainer.getUsers().get(2);
            Bike bikeOfUser3 = user3.getBike();

            assertEquals("Jarl", user1.getUsername());
            assertEquals("jarl123", user1.getPassword());
            assertEquals(null, user1.getBike());

            assertEquals("Jon", user2.getUsername());
            assertEquals("jon123", user2.getPassword());
            assertEquals(null, user2.getBike());

            assertEquals("Mikkel", user3.getUsername());
            assertEquals("mikkel123", user3.getPassword());
            assertEquals(bikeOfUser3, user3.getBike());
            assertEquals("BIKEIDN2", user3.getBike().getID());
            assertEquals("Tandemsykkel", user3.getBike().getType());
            assertEquals("Gul", user3.getBike().getColour());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
