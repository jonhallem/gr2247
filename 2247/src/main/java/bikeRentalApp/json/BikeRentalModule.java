package bikeRentalApp.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bikeRentalApp.core.Bike;
import bikeRentalApp.core.Place;
import bikeRentalApp.core.User;


@SuppressWarnings("serial")
public class BikeRentalModule extends SimpleModule {
    
    private static final String NAME = "BikeRentalModule";
  
  public BikeRentalModule() {

    super(NAME, Version.unknownVersion());
    addSerializer(User.class, new UserSerializer());
    
    addSerializer(Bike.class, new BikeSerializer());

    addSerializer(Place.class, new PlaceSerializer());
    
  }

  public static void main(String[] args) {
    User user = new User("name", "pass12");
    Bike bike = new Bike("BIKEIDN1", "Landeveissykkel", "Blå");

    user.setBike(bike);
    System.out.println(user);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new BikeRentalModule());

    try {
        System.out.println(mapper.writeValueAsString(user));
    } catch (Exception e) {
        System.err.println("Error");
        e.printStackTrace();
    }
}

}

