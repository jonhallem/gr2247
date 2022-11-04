package bikerentalapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import bikerentalapp.core.Bike;
import bikerentalapp.core.User;

public class UserTest {

    @Test
    @DisplayName("Tester det å opprette en ny bruker")
    void testNewUser() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "test123"); }, "IllegalArgument skal utløses om brukernavnet er 'null'.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User(" ", "test123"); }, "IllegalArgument skal utløses om brukernavnet er blankt");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("a1", "test123"); }, "IllegalArgument skal utløses om brukernavnet er for kort");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("@@@@", "test123"); }, "IllegalArgument skal utløses om brukernavnet inneholder noe annet enn bokstaver og tall");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new User("testUsername", null); }, "IllegalArgument skal utløses om passord er 'null'.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("testUsername", ""); }, "IllegalArgument skal utløses om passord er blankt.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("testUsername", "p1"); }, "IllegalArgument skal utløses om passord er kortere enn tre tegn.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("testUsername", "passord"); }, "IllegalArgument skal utløses om passordet ikke inneholder tall og bokstaver.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("testUsername", "123456"); }, "IllegalArgument skal utløses om passordet ikke inneholder tall og bokstaver.");
    }

    @Test
    @DisplayName("Tester det å legge til sykkler til en bruker. ")
    void testAddBike() {
        User user = new User("username", "password1234");
        Bike bike = new Bike("TESTIDN1", "Landeveissykkel", "Blå");
        
        assertEquals(user.getBike(), null, "getBike skal returnere null om ikke noe Bike er satt");
        
        // Setter bike til user, oppretter bike2, og forsøker å legge bike2 til bruker. 
        user.setBike(bike);

        assertEquals(user.getBike().getID(), "TESTIDN1", "Sykkel med riktig ID skal være satt");

        Bike bike2 = new Bike("TESTIDN2", "Terrengsykkel", "Rød");
        assertThrows(IllegalStateException.class, () -> {
            user.setBike(bike2); }, "IllegalState skal utløses dersom du setter en ny sykkel for en bruker som allerede har en sykkel.");
    }

    @Test
    @DisplayName("Tester det å fjerne sykkler fra en bruker ")
    void testReturnAndRomeveBike() {
        User user = new User("username", "password1234");
        Bike bike = new Bike("TESTIDN1", "Landeveissykkel", "Blå");
        user.setBike(bike);

        assertEquals(user.removeAndReturnBike(), bike, "removeAndReturnBike skal returnere et bike-objekt");
        assertEquals(user.getBike(), null, "getBike skal returnere null om removeAndReturnBike er utført");
        assertThrows(IllegalStateException.class, () -> {
            user.removeAndReturnBike(); }, "IllegalState skal utløses dersom du prøver å fjerne en sykkel når du ikke har satt en. ");
    
    }

}
