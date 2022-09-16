package BikeRentalApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    
    //User user;

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
    @DisplayName("Tester det å opprette en ny bruker")
    void testAddBike() {
        User user = new User("username", "password1234");
        
    }

}
