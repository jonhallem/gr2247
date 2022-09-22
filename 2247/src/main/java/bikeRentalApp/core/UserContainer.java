package bikeRentalApp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserContainer implements Iterable<User> {
    
    private List<User> users = new ArrayList<>();

    public UserContainer(List<User> userList) {
        this.users = userList;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User findUser(String userName) {
        return this.users.stream().filter(user -> user.getUsername().equals(userName)).findFirst().get();
    }

    @Override
    public Iterator<User> iterator() {
        return this.users.iterator();
    }
}
