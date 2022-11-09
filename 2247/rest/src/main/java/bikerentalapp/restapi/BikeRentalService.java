package bikerentalapp.restapi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bikerentalapp.core.BikeRentalManager;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.json.BikeRentalPersistence;

/**
 * The top-level rest service for PlaceContainer.
 */
@Path(BikeRentalService.BIKERENTAL_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class BikeRentalService {

    public static final String BIKERENTAL_SERVICE_PATH = "bikerental";

    private static final Logger LOG = LoggerFactory.getLogger(BikeRentalService.class);

    @Context
    private BikeRentalManager bikeRentalManager;

    @Context
    private BikeRentalPersistence bikeRentalPersistence;

    /**
     * The root resource, i.e. /placecontainer
     *
     * @return the PlaceContainer
     */
    @GET
    public BikeRentalManager getBikeRentalManager() {
        LOG.debug("getBikeRenatlManager: " + this.bikeRentalManager);
        return this.bikeRentalManager;
    }

    /**
     * Returns the PlaceContainer object
     * (as a resource to support chaining path elements).
     * 
     * @return the User
     */
    @Path("/placecontainer")
    public PlaceContainerResource getPlaceContainer() {
        LOG.debug("Sub-resource for PlaceContainer");
        return new PlaceContainerResource(this.bikeRentalManager);
    }

    /**
     * Returns the User with the provided name
     * (as a resource to support chaining path elements).
     * This supports all requests referring to Users by name.
     *
     * @param name the name of the todo list
     * 
     * @return the User
     */
    @Path("/user/{name}/{password}")
    public UserResource getUser(@PathParam("name") String name, @PathParam("password") String password) {
        User user = this.bikeRentalManager.getBikeRentalPersistence().readUserContainer().findUser(name);
        LOG.debug("Sub-resource for User " + name);
        UserResource userResource = new UserResource(this.bikeRentalManager, name, password);
        return userResource;
    }

}
