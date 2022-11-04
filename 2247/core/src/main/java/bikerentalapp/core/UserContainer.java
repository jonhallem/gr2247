package bikerentalapp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code UserContainer} is a iterable class, holding a series of {@code User} objects. 
 */

public class UserContainer implements Iterable<User> {
    
    private List<User> users = new ArrayList<>();

    /**
     * Constructs a {@code UserContainer} object with a list of {@code User} objects. 
     * @param userList
     */
    public UserContainer(List<User> userList) {
        this.users = userList;
    }

    /**
     * Returns a new list of the registered {@code User} objects.
     * @return List
     */
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Adds a {@code User} object to the list of users, {@code users}. 
     * @param user
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Revmoes a {@code User} object to the list of users, {@code users}. 
     * This method is only used for testing purposes for now.
     * @param username
     */
    public void removeUser(String username) {

        for (User user : getUsers()) {
            if (user.getUsername().equals(username)) {
                 this.users.remove(user);
            }
        }
    }



    /**
     * Finds a {@code User} object with the given {@code username} in the list of users, {@code users}, and returns it. 
     * @param username
     * @return User
     * @throws IllegalArgumentException If there is no {@code User} with the given {@code username}.  
     */
    public User findUser(String username) {
        return this.users.stream().filter(user -> user.getUsername().equals(username)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Det finnes ingen bruker med dette brukernavnet."));
    }

    @Override
    public Iterator<User> iterator() {
        return this.users.iterator();
    }
}
