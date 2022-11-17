package bikerentalapp.ui;

import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.json.BikeRentalPersistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Class that implements the interface BikeRentalManagerAccess. Makes the model
 * accessable to the UI through the REST API.
 */
public class RemoteBikeRentalManagerAccess implements BikeRentalManagerAccess {

    private final URI endpointBaseUri;

    private static final String APPLICATION_JSON = "application/json";

    private static final String ACCEPT_HEADER = "Accept";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private ObjectMapper objectMapper;

    /**
     * Constructor which instantiates a {@code RemoteBikeRentalManagerAccess} object
     * with a base URI and a new {@code ObjectMapper} object.
     *
     * @param endpointBaseUri the base URI.
     */
    public RemoteBikeRentalManagerAccess(URI endpointBaseUri) {
        BikeRentalPersistence bikeRentalPersistence = new BikeRentalPersistence();
        this.endpointBaseUri = endpointBaseUri;
        this.objectMapper = bikeRentalPersistence.getObjectMapper();
    }

    @Override
    public User signUp(String username, String password) throws IOException {

        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("signUp"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
                .POST(BodyPublishers
                        .ofString("usernameAndPassword=" + uriParam(username + "/" + password)))
                .build();
        System.out.println("REQUEST: " + request);

        try {
            return this.sendHttpRequestsAndReadUserFromResponseString(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User logIn(String username, String password) throws IOException {

        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("logIn"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
                .POST(BodyPublishers
                        .ofString("usernameAndPassword=" + uriParam(username + "/" + password)))
                .build();
        System.out.println("REQUEST: " + request);

        try {
            return this.sendHttpRequestsAndReadUserFromResponseString(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User setUserPassword(String username, String password) throws IOException {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("setPassword"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
                .POST(BodyPublishers
                        .ofString("usernameAndPassword=" + uriParam(username + "/" + password)))
                .build();
        System.out.println("REQUEST: " + request);

        try {
            return this.sendHttpRequestsAndReadUserFromResponseString(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User rentBike(String placeName, String bikeId, String username)
            throws IOException {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("rentBike"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
                .POST(BodyPublishers
                        .ofString(
                                "placeNameAndBikeIdAndUsername="
                                        + uriParam(placeName + "/" + bikeId + "/" + username)))
                .build();
        System.out.println("REQUEST: " + request);

        try {
            return this.sendHttpRequestsAndReadUserFromResponseString(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User deliverBike(String username, String placeName) throws IOException {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("deliverBike"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED)
                .POST(BodyPublishers
                        .ofString(
                                "usernameAndPlaceName=" + uriParam(username + "/" + placeName)))
                .build();
        System.out.println("REQUEST: " + request);

        try {
            return this.sendHttpRequestsAndReadUserFromResponseString(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a request to server to return a {@code PlaceContainer} object that
     * reflects the place persistence in the model.
     *
     * @return a placeContainer.
     */
    @Override
    public PlaceContainer getPlaceContainer() {
        PlaceContainer placeContainer = null;
        HttpRequest request = HttpRequest
                .newBuilder(endpointBaseUri.resolve("getPlaceContainer"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            placeContainer = objectMapper.readValue(responseString, PlaceContainer.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return placeContainer;
    }

    /**
     * Returns the set {@code URI} object.
     *
     * @return a {@code URI} object.
     */
    public URI getUri() {
        return this.endpointBaseUri;
    }

    /**
     * Creates and returns a String representing a URI parameter.
     *
     * @param string URI parameter
     * @return the URI parameter as a String.
     */
    private String uriParam(String string) {
        return URLEncoder.encode(string, StandardCharsets.UTF_8);
    }

    /**
     * Sends the given http request and returns the {@code User} object created by
     * the reponse, or throws an exception with the returned exception message.
     *
     * @param request the http request to send.
     * @return a {@code User} object derived from the response.
     * @throws InterruptedException if an error happens during sending/recieving
     *                              http-request/-response.
     * @throws IOException          if the repons returns that an exception is
     *                              thrown in the model on the server side.
     */
    private User sendHttpRequestsAndReadUserFromResponseString(HttpRequest request)
            throws InterruptedException, IOException {
        final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                HttpResponse.BodyHandlers.ofString());

        String responseString = response.body();
        System.out.println("RESPONSE: " + responseString);

        if (responseString.startsWith("{")) {
            User verifiedUser = objectMapper.readValue(responseString, User.class);
            return verifiedUser;
        } else {
            throw new IOException(responseString);
        }
    }
}
