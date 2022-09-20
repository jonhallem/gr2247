package bikeRentalApp.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BikeRentalDataHandler {



    public List<User> readUsers() throws FileNotFoundException {

        List<User> listOfUsers = new ArrayList<>();

        try(Scanner scanner = new Scanner(getSavedUsersFile())) {
            while(scanner.hasNextLine()) {
                String[] unitProps = scanner.nextLine().split(";");
                if(unitProps.length>3) {
                    User user = new User(unitProps[0], unitProps[1], new Bike(unitProps[2], unitProps[3], unitProps[4]));
                    listOfUsers.add(user);
                } else {
                    User user = new User(unitProps[0], unitProps[1]);
                    listOfUsers.add(user);
                }
            }
        }
        return listOfUsers;
    }

    public void writeUsers(List<User> users) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(getSavedUsersFile())) {
            System.out.println("file: " + getSavedUsersFile());
            for (User user : users) {
                if (user.getBike()!=null) {
                writer.println(String.format("%s;%s;%s;", user.getUsername(), user.getPassword(), 
                user.getBike().getID(), user.getBike().getType(), user.getBike().getColour()));
                } else {
                    writer.println(String.format("%s;%s;%s;", user.getUsername(), user.getPassword(), "null"));
                }
            }
        }
    }

    public File getSavedUsersFile() {
        return getSavedUsersPath().resolve("savedUsers.txt").toFile();
    }

    public static Path getSavedUsersPath() {
        return Path.of(System.getProperty("user.dir"), "src", "main", "resources", "savedUsers");
    }
}
