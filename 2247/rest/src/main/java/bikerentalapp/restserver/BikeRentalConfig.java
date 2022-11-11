package bikerentalapp.restserver;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import bikerentalapp.core.BikeRentalManager;
//TODO: Nødvendig?? 
//import bikerentalapp.json.BikeRentalPersistence;
import bikerentalapp.restapi.BikeRentalService;

public class BikeRentalConfig extends ResourceConfig {

    private BikeRentalManager bikeRentalManager;
    // TODO: Nødvendig??
    // private BikeRentalPersistence bikeRentalPersistence;

    /**
     * Initialize{@code BikeRentalConfig} with bikeRentalManager.
     * 
     * @param bikeRentalManager
     */
    public BikeRentalConfig(BikeRentalManager bikeRentalManager) {
        this.bikeRentalManager = bikeRentalManager;

        register(BikeRentalService.class);
        register(BikeRentalModuleObjectMapperProvider.class);
        register(JacksonFeature.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BikeRentalConfig.this.bikeRentalManager);
                // bind(BikeRentalConfig.this.bikeRentalManager.getBikeRentalPersistence());
            }
        });
    }

    /**
     * Initialize {@code BikeRentalConfig} with a new {@code BikeRentalManager}.
     */
    public BikeRentalConfig() {
        this(new BikeRentalManager());
    }

    // TODO: NØDVENDIG??
    /**
     * Returns the {@code BikeRentalManager} object held by
     * {@code BikeRentalConfig}.
     * 
     * @return bikeRentalManager
     */
    public BikeRentalManager getBikeRentalManager() {
        return this.bikeRentalManager;
    }

}
