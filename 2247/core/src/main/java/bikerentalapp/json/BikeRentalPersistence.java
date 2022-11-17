package bikerentalapp.json;

import bikerentalapp.core.PlaceContainer;
import bikerentalapp.core.UserContainer;
import bikerentalapp.json.internal.BikeRentalModule;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * {@code BikeRentalPersistence} holds a {@code mapper} and a {@code writer},
 * used to write {@code PlaceContainer}
 * objects and {@code UserContainer} objects to file, as well as read them.
 */
public class BikeRentalPersistence {

    private static ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter writer;

    // Konstruktør

    /**
     * Constructs a new {@code BikeRentalPersistence} object, used to write
     * {@code PlaceContainer}
     * objects and {@code UserContainer} objects to file, as well as read them.
     */
    public BikeRentalPersistence() {
        mapper.registerModule(new BikeRentalModule());
        writer = mapper.writer(new DefaultPrettyPrinter());
    }

    // Persistens av stedene

    /**
     * Writes a {@code PlaceContainer} object to the "places.json" file
     *
     * @param placeContainer the {@code PlaceContainer} object to write to file
     * 
     * @throws IOException if writing {@code placeContainer} fails.
     */
    public void writePlaceContainer(PlaceContainer placeContainer) throws IOException {
        writer.writeValue(this.getFile("places"), placeContainer);
    }

    /**
     * Reads "places.json" and returns a new {@code PlaceContainer} object based on
     * the data
     * in the file
     *
     * @return a {@code PlaceContainer} object
     * 
     * @throws IOException if reading {@code placeContainer} fails.
     */
    public PlaceContainer readPlaceContainer() throws IOException {
        this.ensureSaveFileExists("places");
        return mapper.readValue(this.getFile("places"), PlaceContainer.class);
    }

    // Persistens av brukerne

    /**
     * Writes a {@code UserContainer} object to the "users.json" file
     *
     * @param userContainer the {@code UserContainer} object to write to file
     * 
     * @throws IOException if writing {@code userContainer} fails.
     */
    public void writeUserContainer(UserContainer userContainer) throws IOException {
        writer.writeValue(this.getFile("users"), userContainer);
    }

    /**
     * Reads "users.json" and returns a new {@code UserContainer} object based on
     * the data
     * in the file
     *
     * @return a {@code UserContainer} object
     *
     * @throws IOException if reading {@code userContainer} fails.
     */
    public UserContainer readUserContainer() throws IOException {
        this.ensureSaveFileExists("users");
        return mapper.readValue(this.getFile("users"), UserContainer.class);
    }

    // Støttemetoder for filbehandling

    /**
     * Returns a {@code File} object representing the path to the JSON file with
     * the name "fileName".
     *
     * @param fileName a String, the name of the JSON file (without the .json
     *                 file ending)
     * 
     * @return a {@code File} object
     */
    private File getFile(String fileName) {
        return getSaveFileFolderPath().resolve(fileName + ".json").toFile();
    }

    /**
     * Returns the {@code Path} object to the location of the JSON-file to read from
     * or write to.
     *
     * @return a {@code Path} object
     */
    private Path getSaveFileFolderPath() {
        return Path.of(System.getProperty("user.home"), "BikeRentalApp");
    }

    /**
     * Ensures that the JSON file to read from actually exists before reading,
     * avoiding a FileNotFoundException.
     *
     * @param fileName the name of the file (without the .json file ending)
     *                 to ensure the existanse of
     *
     * @throws IllegalStateException filename already exsists.
     * @throws IOException           writing {@cod userContainer} or
     *                               {@code placeContainer}
     *                               fails.
     */
    private void ensureSaveFileExists(String fileName) throws IOException {
        File file = new File(getFile(fileName).toString());
        if (!file.exists()) {
            Files.createDirectories(getSaveFileFolderPath());
            if (file.createNewFile()) {
                if (fileName.equals("users")) {
                    this.writeUserContainer(new UserContainer(new ArrayList<>()));
                } else {
                    this.writePlaceContainer(this.createDefaultPlaceContainer());
                }
            }
        }
    }

    /**
     * Creates a {@code PlaceContainer} object based on the contents of
     * {@code DefaultPlaceContainer}.
     *
     * @return a {@code PlaceContainer} object.
     * 
     * @throws IOException if there is a mapping error.
     */
    private PlaceContainer createDefaultPlaceContainer() throws IOException {
        return mapper.readValue(DefaultPlaceContainer.getDefaultPlaceContainerString(),
                PlaceContainer.class);
    }

    // Returnere mapper

    /**
     * Returns a mapper containing modules for {@code User}, {@code Bike},
     * {@code Place}, {@code PlaceContainer} and {@code UserContainer}.
     *
     * @return {@code ObjectMapper}
     */
    public ObjectMapper getObjectMapper() {
        return mapper.copy();
    }

}
