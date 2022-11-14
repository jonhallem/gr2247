package bikerentalapp.restserver;

import bikerentalapp.core.BikeRentalManager;
import bikerentalapp.restapi.BikeRentalService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures the rest service, registers JSON support with Jackson, BikeRental
 * Service, and an ObjectMapper to serialize, deserialize.
 */
public class BikeRentalConfig extends ResourceConfig {

    private BikeRentalManager bikeRentalManager;

    /**
     * Initializes the {@code BikeRentalConfig}.
     */
    public BikeRentalConfig() {
        this.bikeRentalManager = new BikeRentalManager();

        register(BikeRentalService.class);
        register(BikeRentalModuleObjectMapperProvider.class);
        register(JacksonFeature.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BikeRentalConfig.this.bikeRentalManager);
            }
        });
    }
}
