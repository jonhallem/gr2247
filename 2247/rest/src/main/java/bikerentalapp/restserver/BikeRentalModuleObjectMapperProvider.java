package bikerentalapp.restserver;

import bikerentalapp.json.BikeRentalPersistence;
import bikerentalapp.json.internal.BikeRentalModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * Provides the Jackson module used for JSON serialization.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BikeRentalModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    /**
     * Constructs a {@code BikeRentalModuleObjectMapperProvider} with a
     * objectMapper, containing a mapper for the {@code BikeRentalPersistence}
     * class.
     */
    public BikeRentalModuleObjectMapperProvider() {
        this.objectMapper = BikeRentalPersistence.getObjectMapper();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BikeRentalModule());
        return objectMapper.copy();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.objectMapper.copy();
    }

}
