package bikerentalapp.json.internal;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;

@SuppressWarnings("serial")
public class BikeRentalModule extends SimpleModule {
    
  private static final String NAME = "BikeRentalModule";
  private static final VersionUtil VERSION_UTIL = new VersionUtil() {
  };
  
  public BikeRentalModule() {

    super(NAME, VERSION_UTIL.version());
    addSerializer(User.class, new UserSerializer());
    addDeserializer(User.class, new UserDeserializer());
    
    addSerializer(Bike.class, new BikeSerializer());
    addDeserializer(Bike.class, new BikeDeserializer());

    addSerializer(Place.class, new PlaceSerializer());
    addDeserializer(Place.class, new PlaceDeserializer());

    addSerializer(PlaceContainer.class, new PlaceContainerSerializer());
    addDeserializer(PlaceContainer.class, new PlaceContainerDeserializer());

    addSerializer(UserContainer.class, new UserContainerSerializer());
    addDeserializer(UserContainer.class, new UserContainerDeserializer());

  }
}

