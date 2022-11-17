package bikerentalapp.ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import bikerentalapp.core.Bike;
import bikerentalapp.core.Place;
import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.UserContainer;
import bikerentalapp.json.BikeRentalPersistence;
import bikerentalapp.core.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RemoteBikeRentalManagerTest {

    private RemoteBikeRentalManagerAccess bikeRentalManagerAccess;
    private WireMockConfiguration config;
    private WireMockServer wireMockServer;
    private PlaceContainer placeContainerBeforeTest;
    private UserContainer userContainerBeforeTest;

    @BeforeAll
    public void startWireMockServerAndSetup() throws URISyntaxException, IOException {
        config = WireMockConfiguration.wireMockConfig().port(8089);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        bikeRentalManagerAccess = new RemoteBikeRentalManagerAccess(
                new URI("http://localhost:" + wireMockServer.port() + "/bikerental"));
        this.placeContainerBeforeTest = new BikeRentalPersistence().readPlaceContainer();
        PlaceContainer placeContainer = new PlaceContainer(new ArrayList<>());
        placeContainer.addPlace("Bymarka", 20);
        Bike bike = new Bike("BIKEID01", "Terrengsykkel", "Svart");
        placeContainer.findPlace("Bymarka").addBike(bike);
        new BikeRentalPersistence().writePlaceContainer(placeContainer);
        this.userContainerBeforeTest = new BikeRentalPersistence().readUserContainer();

    }

    @Test
    public void testSignUp() throws IOException {

        stubFor(post(urlEqualTo("/signUp"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"username\":\"TestUser999\",\"password\":\"P123\",\"bike\":null}")));

        User user = this.bikeRentalManagerAccess.signUp("TestUser999", "P123");
        assertEquals("TestUser999", user.getUsername());
        assertEquals("P123", user.getPassword());
        assertEquals(null, user.getBike());
    }

    @Test
    public void testLogIn() throws IOException {

        stubFor(post(urlEqualTo("/logIn"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"username\":\"TestUser999\",\"password\":\"P123\",\"bike\":null}")));

        User user = this.bikeRentalManagerAccess.logIn("TestUser999", "P123");
        assertEquals("TestUser999", user.getUsername());
        assertEquals("P123", user.getPassword());
        assertEquals(null, user.getBike());
    }

    @Test
    public void testSetPassword() throws IOException {

        stubFor(post(urlEqualTo("/setPassword"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"username\":\"TestUser999\",\"password\":\"P1234\",\"bike\":null}")));

        User user = this.bikeRentalManagerAccess.setUserPassword("TestUser999", "P1234");
        assertEquals("TestUser999", user.getUsername());
        assertEquals("P1234", user.getPassword());
        assertEquals(null, user.getBike());
    }

    @Test
    public void testRentBike() throws IOException {

        stubFor(post(urlEqualTo("/rentBike"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"username\":\"TestUser999\",\"password\":\"P1234\",\"bike\":{\"iD\":\"BIKEID01\",\"type\":\"Terrengsykkel\",\"colour\":\"Svart\"}}")));

        User user = this.bikeRentalManagerAccess.rentBike("Bymarka", "BIKEID01", "TestUser999");
        assertEquals("TestUser999", user.getUsername());
        assertEquals("P1234", user.getPassword());
        assertEquals("BIKEID01", user.getBike().getId());
    }

    @Test
    public void testDeliverBike() throws IOException {

        stubFor(post(urlEqualTo("/deliverBike"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"username\":\"TestUser999\",\"password\":\"P1234\",\"bike\":null}")));

        User user = this.bikeRentalManagerAccess.deliverBike("TestUser999", "Bymarka");
        assertEquals("TestUser999", user.getUsername());
        assertEquals("P1234", user.getPassword());
        assertEquals(null, user.getBike());
    }

    @Test
    public void testGetPlaceContainer() {
        stubFor(get(urlEqualTo("/getPlaceContainer"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"places\":[{\"name\":\"Bymarka\",\"maximumNumberOfBikes\":\"20\",\"bikes\":[{\"iD\":\"BIKEID01\",\"type\":\"Terrengsykkel\",\"colour\":\"Svart\"}]}]}")));

        PlaceContainer placeContainer = this.bikeRentalManagerAccess.getPlaceContainer();
        Place place = placeContainer.findPlace("Bymarka");
        assertEquals("Bymarka", place.getName());
        assertEquals(20, place.getMaximumNumberOfBikes());
        assertEquals(1, place.getBikes().size());

    }

    @AfterAll
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @AfterAll
    public void replaceContainers() throws IOException {
        new BikeRentalPersistence().writePlaceContainer(placeContainerBeforeTest);
        new BikeRentalPersistence().writeUserContainer(userContainerBeforeTest);
    }
}
