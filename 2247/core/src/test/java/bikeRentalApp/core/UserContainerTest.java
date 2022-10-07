package bikeRentalApp.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;


public class UserContainerTest {

    private UserContainer userContainer;
    
    @BeforeEach
    public void setUp() {
        System.out.println("Initialiserer, setup.");
        List<User> users = new ArrayList<>();
        this.userContainer = new UserContainer(users);
    }

    @Test
    @DisplayName("Tester det å legge til brukere")
    void testUserFunctions_addUser() {
        List<User> usersTestList = new ArrayList<>();

        assertEquals(
            userContainer.getUsers(), usersTestList
            , "Listen users skal være tom, ettersom ingen brukere er lagt til");
        
        // Adding a user to the userContainer and testList

        User user1 = new User("user1", "pass1");
        userContainer.addUser(user1);    
        usersTestList.add(user1);

        assertEquals(
            userContainer.getUsers(), usersTestList
            , "Både listen users i userContainer og listen usersTestList skal inneholde brukeren user1");

        // Adding a user to the userContainer, but not the test list
        User user2 = new User("user2", "pass1");
        userContainer.addUser(user2);
        
        assertNotEquals(userContainer.getUsers(), usersTestList,
        "Listen users i userContrainer skal nå inneholde user2, noe som ikke usersTestList skal ha.");
        
    }
    
    @Test
    @DisplayName("Tester det å hente ut brukere")
    void testUserFunctions_getUsers() {
        List<User> usersEmpty = new ArrayList<>();

        assertEquals(
            userContainer.getUsers(), usersEmpty
            , "Listen users skal være tom, ettersom ingen brukere er lagt til");

        // Adding a user to the userContainer
        User user1 = new User("user1", "pass1");
        userContainer.addUser(user1);

        assertTrue(
        userContainer.getUsers().contains(user1)
        , "Listen users i userContainer skal nå inneholde brukeren user1");

    }

    @Test
    @DisplayName("Tester det å finne en bruker")
    void testUserFunctions_findUser() {

        // Add user1 to userContainer, but not user2
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass1");
        userContainer.addUser(user1);    

        assertEquals(
            userContainer.findUser("user1"), user1,
            "findUser med username 'user1' skal gi den samme brukeren som user1");

        assertNotEquals(
            userContainer.findUser("user1"), user2,
            "findUser med username 'user1' skal IKKE gi den samme brukeren som user2");

        assertThrows(IllegalArgumentException.class, () -> {
            userContainer.findUser("Unknown");},
            "Å finne en bruker som ikke eksisterer skal utløse IllegalArgument");
    }
    
    @Test
    @DisplayName("Tester det å iterere over brukere")
    void testIterator() {

        assertFalse(userContainer.iterator().hasNext(),
        "Iterator av users i userContainer skal ikke ha noe neste når det ikke er noen brukere lagt til.");
        
        // Add user1 to userContainer
        User user1 = new User("user1", "pass1");
        userContainer.addUser(user1);

        assertTrue(userContainer.iterator().hasNext(),
        "Iterator av users i userContainer skal returnere true, i og med det er en user i listen.");

        assertEquals(userContainer.iterator().next(), user1,
        "Iterator av users skal returnere user1");


        

    } 

}
