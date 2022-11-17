package bikerentalapp.restapi;

import bikerentalapp.core.BikeRentalManager;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Top level REST service for BikeRentalManager.
 * Defines methods that are used when given server calls are made. Adds support
 * for model access through REST API.
 */
@Path(BikeRentalService.BIKERENTAL_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class BikeRentalService {

    public static final String BIKERENTAL_SERVICE_PATH = "bikerental";

    private static final Logger LOG = LoggerFactory.getLogger(BikeRentalService.class);

    private BikeRentalManager bikeRentalManager = new BikeRentalManager();

    /**
     * REST service method for signups.
     *
     * @param usernameAndPassword a String form param derived from the http
     *                            request-body containing the username and password
     *                            devided by "/".
     * @return a an http response containing the signed up {@code User} object, or
     *         an error message.
     */
    @POST
    @Path("/signUp")
    @Consumes("application/x-www-form-urlencoded")
    public Response signUpAndReturnUser(
            @FormParam("usernameAndPassword") String usernameAndPassword) {
        String[] list = usernameAndPassword.split("/");
        String username = list[0];
        String password = list[1];
        System.out.println("RECIEVED USERNAME: " + username);
        LOG.debug("signUp: " + username + ", " + password);
        try {
            User user = this.bikeRentalManager.signUp(username, password);
            return this.generateOkResponse(user);
        } catch (Exception e) {
            return this.generateExceptionResponse(Response.Status.CONFLICT, e.getMessage());
        }
    }

    /**
     * REST service method for logins.
     *
     * @param usernameAndPassword a String form param derived from the http
     *                            request-body containing the username and password
     *                            devided by "/".
     * @return a an http response containing the logged in {@code User} object, or
     *         an error message.
     */
    @POST
    @Path("/logIn")
    @Consumes("application/x-www-form-urlencoded")
    public Response logInAndReturnUser(
            @FormParam("usernameAndPassword") String usernameAndPassword) {
        String[] list = usernameAndPassword.split("/");
        String username = list[0];
        String password = list[1];
        LOG.debug("logIn: " + username + ", " + password);
        try {
            User user = this.bikeRentalManager.logIn(username, password);
            return this.generateOkResponse(user);
        } catch (Exception e) {
            return this.generateExceptionResponse(Response.Status.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * REST service method for setting the password of a user.
     *
     * @param usernameAndPassword a String form param derived from the http
     *                            request-body containing the username and new
     *                            password devided by "/".
     * @return a an http response containing the {@code User} object with the
     *         changed password, or an error message.
     */
    @POST
    @Path("/setPassword")
    @Consumes("application/x-www-form-urlencoded")
    public Response setPasswordAndReturnUser(
            @FormParam("usernameAndPassword") String usernameAndPassword) {
        String[] list = usernameAndPassword.split("/");
        String username = list[0];
        String password = list[1];
        LOG.debug("setPassword: " + username + ", " + password);
        try {
            User user = this.bikeRentalManager.setUserPassword(username, password);
            return this.generateOkResponse(user);
        } catch (Exception e) {
            return this.generateExceptionResponse(Response.Status.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * REST service method for renting a bike.
     *
     * @param placeNameAndBikeIdAndUsername a String form param derived from the
     *                                      http request-body containing the
     *                                      placeName, bikeId and username devided
     *                                      by "/".
     * @return a an http response containing the {@code User} object with the
     *         rented {@code Bike} object, or an error message.
     */
    @POST
    @Path("/rentBike")
    @Consumes("application/x-www-form-urlencoded")
    public Response rentBikeAndReturnUser(
            @FormParam("placeNameAndBikeIdAndUsername") String placeNameAndBikeIdAndUsername) {
        String[] list = placeNameAndBikeIdAndUsername.split("/");
        String placeName = list[0];
        String bikeId = list[1];
        String username = list[2];
        LOG.debug("rentBike: " + placeName + ", " + bikeId + ", " + username);
        try {
            User user = this.bikeRentalManager.rentBike(placeName, bikeId, username);
            return this.generateOkResponse(user);
        } catch (Exception e) {
            return this.generateExceptionResponse(Response.Status.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * REST service method for delivering a bike.
     *
     * @param usernameAndPlaceName a String form param derived from the
     *                             http request-body containing the
     *                             username and placeName devided
     *                             by "/".
     * @return a an http response containing the {@code User} object without the
     *         delivered {@code Bike} object, or an error message.
     */
    @POST
    @Path("/deliverBike")
    @Consumes("application/x-www-form-urlencoded")
    public Response deliverBikeAndReturnUser(
            @FormParam("usernameAndPlaceName") String usernameAndPlaceName) {
        String[] list = usernameAndPlaceName.split("/");
        String username = list[0];
        String placeName = list[1];
        LOG.debug("deliverBike: " + username + ", " + placeName);
        try {
            User user = this.bikeRentalManager.deliverBike(username, placeName);
            return this.generateOkResponse(user);
        } catch (Exception e) {
            return this.generateExceptionResponse(Response.Status.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * Defines the http request format and method to get a {@code PlaceContainer}
     * object that represents the places stored on the server.
     *
     * @return a {@code PlaceContainer} object in JSON format, if it can be read.
     *         Otherwise, {@code null}.
     */
    @GET
    @Path("/getPlaceContainer")
    @Produces(MediaType.APPLICATION_JSON)
    public PlaceContainer getPlaceContainer() {
        LOG.debug("getPlaceContainer");
        try {
            return this.bikeRentalManager.getPlaceContainer();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Generates a {@code Response} object with response status "OK" and a
     * {@code User} object within its body.
     *
     * @param user the {@code User} object to attatch.
     * @return the generated {@code Response} object.
     */
    private Response generateOkResponse(User user) {
        return Response
                .status(Response.Status.OK)
                .entity(user)
                .build();
    }

    /**
     * Generates a {@code Response} object with a given response status and an
     * exception message within its body.
     *
     * @param responseStatus   an appropriate response {@code Status} object.
     * @param exceptionMessage an exception message.
     * @return the generated {@code Response} object.
     */
    private Response generateExceptionResponse(Status responseStatus, String exceptionMessage) {
        return Response
                .status(responseStatus)
                .entity(exceptionMessage)
                .build();
    }

}
