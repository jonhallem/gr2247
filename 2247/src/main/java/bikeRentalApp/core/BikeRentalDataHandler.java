package bikeRentalApp.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BikeRentalDataHandler {

    private List<Bike> bikeList;

    private List<User> userList;

    private List<Place> placeList;

       //--------------------- SAVING AND LOADING -------------------------


    //hver bruker er lagt til en egen linje med formatet "brukernavn;passord"
    public void saveUser(String file, User user) throws IOException {

        Files.createDirectories(getScoreBoardPath());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFile(file), true))) {

            bw.write(user.getUsername() + ";" + user.getPassword() + "\n");
            
            bw.flush();
            bw.close();
        }
    }

    public List<User> loadUsers (String file) throws IOException {
        try(Scanner scanner = new Scanner(getFile(file))) {

            //skanner hver linje for en bruker, og laster det inn i en liste som legges til i managers
            while (scanner.hasNextLine()) {
                String[] savedata = scanner.nextLine().split(";");
                User user = new User(savedata[0], savedata[1]);
                userList.add(user);
                
            }
            scanner.close();
            return userList;
        }
    }

    public void savePlace(String file, Place place) throws IOException {

        Files.createDirectories(getScoreBoardPath());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFile(file), true))) {

            bw.write(place.getName() + place.getMaximumNumberOfBikes() + "\n");
            
            bw.flush();
            bw.close();
        }
    }

    public List<Place> loadPlace (String file) throws IOException {
        try(Scanner scanner = new Scanner(getFile(file))) {

            //skanner hver linje for en bruker, og laster det inn i en liste som legges til i managers
            while (scanner.hasNextLine()) {
                String[] savedata = scanner.nextLine().split(";");

                // TODO: får ikke lov til å endre til int?
                //Place place = new Place(savedata[0], savedata[1].parseInt());
                //placeList.add(place);
                
            }
            scanner.close();
            return placeList;
        }
    }


    public void saveBike(String file, Bike bike) throws IOException {

        Files.createDirectories(getScoreBoardPath());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFile(file), true))) {

            //TODO: må legge inn bikes på en fornuftig måte
            bw.write(bike.getID() + bike.getType() + bike.getColour() + "\n");
            
            bw.flush();
            bw.close();
        }
    }

    public List<Bike> loadBike (String file) throws IOException {
        try(Scanner scanner = new Scanner(getFile(file))) {

            //skanner hver linje for en bruker, og laster det inn i en liste som legges til i managers
            while (scanner.hasNextLine()) {
                String[] savedata = scanner.nextLine().split(";");

                // TODO: får ikke lov til å endre til int?
                Bike bike = new Bike(savedata[0], savedata[1], savedata[2]);
                bikeList.add(bike);
                
            }
            scanner.close();
            return bikeList;
        }
    }




    // -------------------------- METHODS FOR GUI LOGIC ---------------------


    // --------------------- SUPPORTING METHODS ---------------------------

    // private static File getFile(String filename) {
    //     return new File(BikeRentalDataHandler.class.getResource("data/").getFile() + filename + ".txt");
    // }


    
    public File getFile(String filename) {
        return getScoreBoardPath().resolve(filename + ".txt").toFile();
    }

    //TODO: path.of fungerer ikke på min mac - jon
    private static Path getScoreBoardPath() {
        return Path.of(System.getProperty("user.home"), "BikeRentalApp", "BikeRentalData");
    }



                    // Mikkel sin kode under:



    // public List<User> readUsers() throws FileNotFoundException {

    //     List<User> listOfUsers = new ArrayList<>();

    //     try(Scanner scanner = new Scanner(getSavedUsersFile())) {
    //         while(scanner.hasNextLine()) {
    //             String[] unitProps = scanner.nextLine().split(";");
    //             if(unitProps.length>3) {
    //                 User user = new User(unitProps[0], unitProps[1], new Bike(unitProps[2], unitProps[3], unitProps[4]));
    //                 listOfUsers.add(user);
    //             } else {
    //                 User user = new User(unitProps[0], unitProps[1]);
    //                 listOfUsers.add(user);
    //             }
    //         }
    //     }
    //     return listOfUsers;
    // }

    // public void writeUsers(List<User> users) throws FileNotFoundException {
    //     try(PrintWriter writer = new PrintWriter(getSavedUsersFile())) {
    //         System.out.println("file: " + getSavedUsersFile());
    //         for (User user : users) {
    //             if (user.getBike()!=null) {
    //             writer.println(String.format("%s;%s;%s;", user.getUsername(), user.getPassword(), 
    //             user.getBike().getID(), user.getBike().getType(), user.getBike().getColour()));
    //             } else {
    //                 writer.println(String.format("%s;%s;%s;", user.getUsername(), user.getPassword(), "null"));
    //             }
    //         }
    //     }
    // }

    // public File getSavedUsersFile() {
    //     return getSavedUsersPath().resolve("savedUsers.txt").toFile();
    // }

    // public static Path getSavedUsersPath() {
    //     return Path.of(System.getProperty("user.dir"), "src", "main", "resources", "savedUsers");
    // }
}
