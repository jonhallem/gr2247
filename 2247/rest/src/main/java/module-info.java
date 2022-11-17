module bikerentalapp.rest {
    requires jakarta.ws.rs;

    requires jersey.common;
    requires jersey.server;
    requires jersey.media.json.jackson;

    requires org.glassfish.hk2.api;
    requires org.slf4j;

    requires bikerentalapp.core;

    opens bikerentalapp.restapi to jersey.server;
}
