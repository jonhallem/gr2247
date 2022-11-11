package bikerentalapp.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bikerentalapp.core.BikeRentalManager;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.UserContainer;

/**
 * Top level REST service for BikeRentalManager.
 * 
 * Defines methods that are used when given server calls are made. Adds support
 * for model access through REST API.
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

    // @Context
    private BikeRentalManager bikeRentalManager = new BikeRentalManager();

    @GET
    @Path("/getPlaceContainer")
    @Produces(MediaType.APPLICATION_JSON)
    public PlaceContainer getPlaceContainer() {
        LOG.debug("getPlaceContainer");
        try {
            return this.bikeRentalManager.getPlaceContainer();
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @GET
    @Path("/getUserContainer")
    @Produces(MediaType.APPLICATION_JSON)
    public UserContainer getUserContainer() {
        LOG.debug("getUserContainer");
        try {
            return this.bikeRentalManager.getBikeRentalPersistence().readUserContainer();
        } catch (Exception e) {
            // TODO
            return null;
        }
    }

    @POST
    @Path("/updatePlaceContainer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean updatePlaceContainer(PlaceContainer placeContainer) {
        LOG.debug("updatePlaceConatiner", placeContainer);
        try {
            this.bikeRentalManager.getBikeRentalPersistence().writePlaceContainer(placeContainer);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
        }
    }

    @POST
    @Path("/updateUserContainer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean updateUserContainer(UserContainer userContainer) {
        LOG.debug("updateUserConatiner", userContainer);
        try {
            this.bikeRentalManager.getBikeRentalPersistence().writeUserContainer(userContainer);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
        }
    }

    /*
     * @PUT
     * 
     * @Path("/signup")
     * 
     * @Consumes("application/x-www-form-urlencoded")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public User signUpAndReturnUser(@FormParam("username") String
     * username, @FormParam("password") String password) {
     * LOG.debug("signup: " + username + ", " + password);
     * try {
     * return this.bikeRentalManager.signUp(username, password);
     * } catch (Exception e) {
     * // TODO
     * return null;
     * }
     * }
     * 
     * @GET
     * 
     * @Path("/login")
     * 
     * @Consumes("application/x-www-form-urlencoded")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public User logInAndReturnUser(@FormParam("username") String
     * username, @FormParam("password") String password) {
     * LOG.debug("login: " + username + ", " + password);
     * try {
     * return this.bikeRentalManager.logIn(username, password);
     * } catch (Exception e) {
     * // TODO
     * return null;
     * }
     * }
     * 
     * @POST
     * 
     * @Path("/setpassword")
     * 
     * @Consumes("application/x-www-form-urlencoded")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public User changePasswordAndReturnUser(@FormParam("username") String
     * username,
     * 
     * @FormParam("newpassword") String newpassword) {
     * LOG.debug("changePassword: " + username + ", " + newpassword);
     * try {
     * return this.bikeRentalManager.setUserPassword(username, newpassword);
     * } catch (Exception e) {
     * // TODO
     * return null;
     * }
     * }
     * 
     * @POST
     * 
     * @Path("/rentbike")
     * 
     * @Consumes("application/x-www-form-urlencoded")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public User rentBikeAndReturnUser(@FormParam("placename") String placeName,
     * 
     * @FormParam("bikeid") String bikeId, @FormParam("username") String username) {
     * LOG.debug("rentBike: " + placeName + ", " + bikeId + ", " + username);
     * try {
     * return this.bikeRentalManager.rentBike(placeName, bikeId, username);
     * } catch (Exception e) {
     * // TODO
     * return null;
     * }
     * }
     * 
     * @POST
     * 
     * @Path("/deliverbike")
     * 
     * @Consumes("application/x-www-form-urlencoded")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public User deliverBikeAndReturnUser(@FormParam("username") String username,
     * 
     * @FormParam("placename") String placeName) {
     * LOG.debug("deliverBike: " + username + ", " + placeName);
     * try {
     * return this.bikeRentalManager.deliverBike(username, placeName);
     * } catch (Exception e) {
     * // TODO
     * return null;
     * }
     * }
     * 
     * @GET
     * 
     * @Path("/getplaces")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public PlaceContainer getPlaces() {
     * LOG.debug("getPlaces");
     * try {
     * // Her må metoder byttes på slik at controller bruker placecontainer i stedet
     * // for en liste med places
     * return
     * this.bikeRentalManager.getBikeRentalPersistence().readPlaceContainer();
     * } catch (Exception e) {
     * // TODO
     * return null;
     * }
     * }
     */

}
