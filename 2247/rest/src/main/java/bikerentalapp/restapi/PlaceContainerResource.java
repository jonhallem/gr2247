package bikerentalapp.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bikerentalapp.core.BikeRentalManager;
import bikerentalapp.core.PlaceContainer;

public class PlaceContainerResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceContainerResource.class);

    private final BikeRentalManager bikeRentalManager;

    /**
     * Initializes a PlaceContainerResourse with appropriate context information.
     * Each method will check and use what it needs.
     *
     * @param bikeRentalManager the BikeRentalManager
     */
    public PlaceContainerResource(BikeRentalManager bikeRentalManager) {
        this.bikeRentalManager = bikeRentalManager;
    }

    /**
     * Gets the PlaceContainer.
     *
     * @return the PlaceContainer
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlaceContainer getPlaceContainer() throws IOException {
        LOG.debug("getPlaceContainer()");
        return this.bikeRentalManager.getBikeRentalPersistence().readPlaceContainer();
    }

    /**
     * Replaces the PlaceContainer.
     *
     * @param PlaceContainer the new PlaceContainer to use
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean putPlaceContainer(PlaceContainer placeContainer) {
        LOG.debug("putPlaceContainer({})", placeContainer);
        try {
            this.bikeRentalManager.getBikeRentalPersistence().writePlaceContainer(placeContainer);
            return true;
        } catch (IOException e) {
            // TODO: handle exception
            return false;
        }
    }

}