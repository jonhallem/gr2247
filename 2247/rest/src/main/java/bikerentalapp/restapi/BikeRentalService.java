package bikerentalapp.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
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
 * The top-level rest service for BikeRental.
 */
@Path(BikeRentalService.BIKERENTAL_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class BikeRentalService {

    // Trenger metoder for:

    // Signup -> endrer modell og returnerer user objekt (PUT)
    // Login -> returnerer user objekt (GET)
    // setUserPassword -> endrer modell og returnerer user objekt (POST)
    // rentBike -> endrer modell og returnerer user objekt (POST)
    // deliverBike -> endrer modell og returnerer user objekt (POST)
    // getPlaces -> returnerer placecontainer med sykler (GET)

    // Trenger aldri å bruke @consume (sender kun strings til server)
    // Må bruke @produces når et user objekt og en placecontainer skal sendes
    // tilbake
    // Må kanskje bruke @Consumes(MediaType.TEXT_PLAIN) for å konsumere string sendt
    // over http? Mulig ikke nødvendig pga queryparam?
    // @QueryParam nyttig for å ta inn strings fra http

    public static final String BIKERENTAL_SERVICE_PATH = "bikerental";

    private static final Logger LOG = LoggerFactory.getLogger(BikeRentalService.class);

    @Context
    private BikeRentalManager bikeRentalManager;

    @Context
    private BikeRentalPersistence bikeRentalPersistence;

    @PUT
    @Path("/signup")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public User signUpAndReturnUser(@FormParam("username") String username, @FormParam("password") String password) {
        LOG.debug("signup: " + username + ", " + password);
        try {
            return this.bikeRentalManager.signUp(username, password);
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @GET
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public User logInAndReturnUser(@FormParam("username") String username, @FormParam("password") String password) {
        LOG.debug("login: " + username + ", " + password);
        try {
            return this.bikeRentalManager.logIn(username, password);
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @POST
    @Path("/setpassword")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public User changePasswordAndReturnUser(@FormParam("username") String username,
            @FormParam("newpassword") String newpassword) {
        LOG.debug("changePassword: " + username + ", " + newpassword);
        try {
            return this.bikeRentalManager.setUserPassword(username, newpassword);
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @POST
    @Path("/rentbike")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public User rentBikeAndReturnUser(@FormParam("placename") String placeName,
            @FormParam("bikeid") String bikeId, @FormParam("username") String username) {
        LOG.debug("rentBike: " + placeName + ", " + bikeId + ", " + username);
        try {
            return this.bikeRentalManager.rentBike(placeName, bikeId, username);
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @POST
    @Path("/deliverbike")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public User deliverBikeAndReturnUser(@FormParam("username") String username,
            @FormParam("placename") String placeName) {
        LOG.debug("deliverBike: " + username + ", " + placeName);
        try {
            return this.bikeRentalManager.deliverBike(username, placeName);
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @GET
    @Path("/getplaces")
    @Produces(MediaType.APPLICATION_JSON)
    public PlaceContainer getPlaces() {
        LOG.debug("getPlaces");
        try {
            // Her må metoder byttes på slik at controller bruker placecontainer i stedet
            // for en liste med places
            return this.bikeRentalManager.getBikeRentalPersistence().readPlaceContainer();
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

}
