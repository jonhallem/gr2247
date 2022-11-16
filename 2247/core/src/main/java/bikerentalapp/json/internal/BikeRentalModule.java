package bikerentalapp.json.internal;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A Jackson module setting up JSON serializers and deserializers for BikeRental
 * model objects.
 */
@SuppressWarnings("serial")
public class BikeRentalModule extends SimpleModule {

    private static final String NAME = "BikeRentalModule";

    /**
     * Initializes the BikeRentalModule with the neccessary serializers and
     * deserializers.
     */
    public BikeRentalModule() {
        super(NAME, Version.unknownVersion());

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
