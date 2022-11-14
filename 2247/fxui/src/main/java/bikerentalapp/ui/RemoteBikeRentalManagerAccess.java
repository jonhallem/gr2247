package bikerentalapp.ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.ObjectMapper;

import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.User;
import bikerentalapp.core.UserContainer;
import bikerentalapp.json.BikeRentalPersistence;

/**
 * Class that implements the interface BikeRentalManagerAccess. Makes the model
 * accessable to the UI through the REST API.
 */
public class RemoteBikeRentalManagerAccess implements BikeRentalManagerAccess {

    private final URI endpointBaseUri;

    private static final String APPLICATION_JSON = "application/json";

    private static final String ACCEPT_HEADER = "Accept";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

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
        System.out.println("OBJECTMAPPER SET: " + objectMapper);
    }

    /**
     * Sends a request to server to return a {@code PlaceContainer} object that
     * reflects the place persistence in the model.
     * 
     * @return a placeContainer.
     */
    private PlaceContainer getPlaceContainerFromServer() {
        PlaceContainer placeContainer = null;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("getPlaceContainer"))
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
     * Sends a request to server to update the model persistence with the given
     * {@code PlaceContainer} object.
     * 
     * The server repiles with false if there was an error.
     *
     * @throws RuntimeException if there is an error sending the request or with
     *                          reading the returned value.
     */
    private void sendPlaceContainerToServer(PlaceContainer placeContainer) {
        try {
            String json = objectMapper.writeValueAsString(placeContainer);
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("updatePlaceContainer"))
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                    .POST(BodyPublishers.ofString(json))
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            Boolean isUpdated = objectMapper.readValue(responseString, Boolean.class);
            if (isUpdated == null || !isUpdated) {
                throw new IOException("Error on server side, could not update database.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a request to server to update the model persistence with the given
     * {@code UserContainer} object.
     * 
     * The server repiles with false if there was an error.
     *
     * @throws RuntimeException if there is an error sending the request or with
     *                          reading the returned value.
     */
    private void sendUserContainerToServer(UserContainer userContainer) {
        try {
            String json = objectMapper.writeValueAsString(userContainer);
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("updateUserContainer"))
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                    .POST(BodyPublishers.ofString(json))
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            Boolean isUpdated = objectMapper.readValue(responseString, Boolean.class);
            if (isUpdated == null || !isUpdated) {
                throw new IOException("Error on server side, could not update database.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a request to server to return a {@code UserContainer} object that
     * reflects the user persistence in the model.
     * 
     * @return a userContainer.
     */
    private UserContainer getUserContainerFromServer() {
        UserContainer userContainer = null;
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("getUserContainer"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            userContainer = objectMapper.readValue(responseString, UserContainer.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userContainer;
    }

    @Override
    public User signUp(String username, String password) throws IOException {
        UserContainer userContainer = this.getUserContainerFromServer();
        for (User user : userContainer.getUsers()) {
            if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Brukernavnet er tatt!");
            }
        }
        User user = new User(username, password);
        userContainer.addUser(user);
        this.sendUserContainerToServer(userContainer);
        return user;
    }

    @Override
    public User logIn(String username, String password) throws IOException, IllegalArgumentException {
        UserContainer userContainer = this.getUserContainerFromServer();
        for (User user : userContainer.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Brukernavn eller passord er ikke gyldig");

    }

    @Override
    public User setUserPassword(String username, String password) throws IllegalArgumentException, IOException {
        UserContainer userContainer = this.getUserContainerFromServer();
        User userFromContainter = userContainer.findUser(username);
        userFromContainter.changePassword(password);
        this.sendUserContainerToServer(userContainer);
        return userFromContainter;
    }

    @Override
    public User rentBike(String placeName, String bikeId, String username)
            throws IOException, IllegalArgumentException {
        UserContainer userContainer = this.getUserContainerFromServer();
        PlaceContainer placeContainer = this.getPlaceContainerFromServer();
        Place placeToRentFromInPlaceContainer = placeContainer.findPlace(placeName);
        if (placeToRentFromInPlaceContainer.getBikes().stream().filter(bike -> bike.getID().equals(bikeId))
                .count() == 0) {
            throw new IllegalArgumentException("Det gitte stedet inneholder ikke den gitte sykkelen.");
        }
        User userToRentBike = userContainer.findUser(username);
        userToRentBike.setBike(placeToRentFromInPlaceContainer.removeAndGetBike(bikeId));
        this.sendUserContainerToServer(userContainer);
        this.sendPlaceContainerToServer(placeContainer);
        return userToRentBike;
    }

    @Override
    public User deliverBike(String username, String placeName) throws IOException {
        UserContainer userContainer = this.getUserContainerFromServer();
        PlaceContainer placeContainer = this.getPlaceContainerFromServer();
        User userToDeliverBike = userContainer.findUser(username);
        Bike bikeToDeliver = userToDeliverBike.removeAndReturnBike();
        placeContainer.findPlace(placeName).addBike(bikeToDeliver);
        this.sendUserContainerToServer(userContainer);
        this.sendPlaceContainerToServer(placeContainer);
        return userToDeliverBike;
    }

    @Override
    public PlaceContainer getPlaceContainer() throws IOException {
        return this.getPlaceContainerFromServer();
    }

    public URI getUri() {
        return this.endpointBaseUri;
    }

}
