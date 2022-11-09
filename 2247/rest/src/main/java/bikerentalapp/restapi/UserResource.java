package bikerentalapp.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private final BikeRentalManager bikeRentalManager;
    private final String name;
    private final String password;
    private final User user;

    @Context
    private BikeRentalPersistence bikeRentalPersistence;

    public void setBikeRentalPersistence(BikeRentalPersistence bikeRentalPersistence) {
        this.bikeRentalPersistence = bikeRentalPersistence;
    }

    /**
     * Initializes this TodoListResource with appropriate context information. Each
     * method will check
     * and use what it needs.
     *
     * @param todoModel the TodoModel, needed for DELETE and rename
     * @param name      the todo list name, needed for most requests
     * @param todoList  the TodoList, or null, needed for PUT
     */
    public UserResource(BikeRentalManager bikeRentalManager, String name, String password) {
        this.bikeRentalManager = bikeRentalManager;
        this.name = name;
        this.password = password;
    }

    /**
     * Gets the corresponding TodoList.
     *
     * @return the corresponding TodoList
     */
    @GET
    public User logInAndGetUser() {
        LOG.debug("logInAndGetUser({})", name);
        try {
            this.user = this.bikeRentalManager.logIn(username, password);
            return this.user;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.toString());
        }
    }

    /**
     * Replaces or adds a TodoList.
     *
     * @param todoListArg the todoList to add
     * @return true if it was added, false if it replaced
     */
    @GET
    public User signUpAndGetUser() {
        LOG.debug("signUpAndGetUser({})", name);
        try {
            this.user = this.bikeRentalManager.signUp(username, password);
            return this.user;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.toString());
        }
    }

    /**
     * Replaces or adds a TodoList.
     *
     * @param todoListArg the todoList to add
     * @return true if it was added, false if it replaced
     */
    @GET
    public User rentBikeAndGetUser(Bike bike) {
        LOG.debug("rentBikeAndGetUser({})", name);
        try {
            this.user = this.bikeRentalManager.rentBike(place, bike, user);
            return this.user;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Adds a TodoList with the given name, if it doesn't exist already.
     *
     * @return true if it was added, false if it replaced
     */
    @PUT
    public boolean putTodoList() {
        return putTodoList(new TodoList(name));
    }

    /**
     * Renames the TodoList.
     *
     * @param newName the new name
     */
    @POST
    @Path("/rename")
    @Consumes("application/x-www-form-urlencoded")
    public boolean renameTodoList(@FormParam("newName") String newName) {
        checkTodoList();
        if (this.todoModel.getTodoList(newName) != null) {
            throw new IllegalArgumentException("A TodoList named \"" + newName + "\" already exists");
        }
        this.todoList.setName(newName);
        autoSaveTodoModel();
        return true;
    }

}
