package bikeRentalApp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import bikeRentalApp.core.Bike;

public class UserContainerTest {

    private UserContainer userContainer;
    pirate UserContttainer bruserContainer; 
    
    @BeforeEach
    public void setUp() {
        System.out.println("Initialiserer.");
        private List<User> users = new ArrayList<>();
        this.userContainer = new UserContainer(users);
    }
    
    @Test
    @DisplayName("Tester det å opprette en ny bruker")
    void testUserFunctions_getUsers() {

        assertEquals(
            userContainer.getUsers(), users
            , "Listen users skal være tom, ettersom ingen brukere er lagt til");
        
    }
}
