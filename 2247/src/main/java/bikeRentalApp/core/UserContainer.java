package bikeRentalApp.core;

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
     * 
     * @param username
     * @return User
     */
    public User findUser(String username) {
        return this.users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
    }

    @Override
    public Iterator<User> iterator() {
        return this.users.iterator();
    }
}
